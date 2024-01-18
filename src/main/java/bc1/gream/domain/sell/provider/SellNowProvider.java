package bc1.gream.domain.sell.provider;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.gifticon.service.GifticonCommandService;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.user.entity.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellNowProvider {

    private final BuyService buyService;
    private final CouponService couponService;
    private final OrderCommandService orderCommandService;
    private final GifticonCommandService gifticonCommandService;
    private final CouponCommandService couponCommandService;

    @Transactional
    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {
        // 해당상품과 가격에 대한 구매입찰

        Order order;

        Buy buy = buyService.getRecentBuyBidOf(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = getCouponFrom(buy);
        // 새로운 주문
        if (coupon != null) {
            // 쿠폰이 존재할 때 쿠폰 상태 변경 및 쿠폰이 있는 order 저장 메소드로 이동
            couponCommandService.changeCouponStatus(coupon, CouponStatus.ALREADY_USED);
            order = orderCommandService.saveOrderOfBuy(buy, user, coupon);
        } else {
            // 쿠폰이 존재하지 않을 때 쿠폰이 없는 order 저장 메소드로 이동
            order = orderCommandService.saveOrderOfBuyNotCoupon(buy, user);
        }

        // 새로운 기프티콘 저장
        gifticonCommandService.saveGifticon(requestDto.gifticonUrl(), order);
        // 판매에 따른 사용자 포인트 충전
        user.increasePoint(order.getExpectedPrice());

        // 구매입찰 삭제
        buyService.delete(buy);

        // 매퍼를 통해 변환
        return OrderMapper.INSTANCE.toSellNowResponseDto(order);
    }

    /**
     * 구매입찰로부터 쿠폰 조회
     *
     * @param buy 구매입찰
     * @return 구매입찰 시 등록된 쿠폰, 없다면 null 반환
     */
    private Coupon getCouponFrom(Buy buy) {
        if (Objects.isNull(buy.getCouponId())) {
            return null;
        }
        // 쿠폰 조회, 사용처리
        Coupon coupon = couponService.findCouponById(buy.getCouponId(), buy.getUser());
        coupon.changeStatus(CouponStatus.ALREADY_USED);
        return coupon;
    }
}
