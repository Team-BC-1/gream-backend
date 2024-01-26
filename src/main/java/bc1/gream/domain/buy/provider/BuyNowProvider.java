package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.service.SellService;
import bc1.gream.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyNowProvider {

    private final SellService sellService;
    private final BuyQueryService buyQueryService;
    private final OrderCommandService orderCommandService;
    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;

    @Transactional
    public BuyNowResponseDto buyNowProduct(User buyer, BuyNowRequestDto requestDto, Long productId) {
        // 해당상품과 가격에 대한 판매입찰
        Sell sell = sellService.getRecentSellBidof(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = getCoupon(requestDto.couponId(), buyer);

        // 새로운 주문
        Order order = saveOrder(sell, buyer, coupon, sell.getGifticon());
        // 해당 판매입찰 삭제
        sellService.delete(sell);

        // 구매가능검증 :: 사용자 포인트가 finalPrice 보다 작다면 예외처리
        // << 이건 컨트롤러 단에서 검증 해줘야하 하지 않을까???
        buyQueryService.userPointCheck(buyer, order.getFinalPrice());

        // 즉시구매자 포인트 감소
        buyer.decreasePoint(order.getFinalPrice());
        // 판매입찰자 포인트 증가
        order.getSeller().increasePoint(order.getExpectedPrice());

        // 매핑
        return OrderMapper.INSTANCE.toBuyNowResponseDto(order);
    }

    private Coupon getCoupon(Long couponId, User buyer) {
        if (couponId != null) {
            Coupon coupon = couponQueryService.checkCoupon(couponId, buyer, CouponStatus.AVAILABLE);
            couponCommandService.changeCouponStatus(coupon, CouponStatus.ALREADY_USED);
            return coupon;
        }
        return null;
    }

    private Order saveOrder(Sell sell, User buyer, Coupon coupon, Gifticon gifticon) {
        if (coupon != null) {
            return orderCommandService.saveOrderOfSell(sell, buyer, coupon, gifticon);
        }
        return orderCommandService.saveOrderOfSellNotCoupon(sell, buyer, gifticon);
    }
}