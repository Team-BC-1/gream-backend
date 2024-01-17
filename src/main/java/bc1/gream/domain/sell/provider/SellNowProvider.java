package bc1.gream.domain.sell.provider;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.domain.gifticon.service.GifticonCommandService;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellNowProvider {

    private final BuyService buyService;
    private final CouponService couponService;
    private final OrderCommandService orderCommandService;
    private final GifticonCommandService gifticonCommandService;

    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {
        // 해당상품과 가격에 대한 구매입찰
        Buy buy = buyService.getRecentBuyBidOf(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = couponService.findCouponById(buy.getCouponId(), buy.getUser());
        // 새로운 주문
        Order order = orderCommandService.saveOrderOfBuy(buy, user, coupon);
        // 새로운 기프티콘 저장
        gifticonCommandService.saveGifticon(requestDto.gifticonUrl(), order);
        // 사용자 쿠폰 사용처리 << 추후 구현 예정
        // 판매에 따른 사용자 포인트 충전
        user.increasePoint(order.getFinalPrice());

        // 구매입찰 삭제
        buyService.delete(buy);

        // 매퍼를 통해 변환
        return OrderMapper.INSTANCE.toSellNowResponseDto(order);
    }
}
