package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.buy.validator.BuyAvailabilityVerifier;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.service.command.SellCommandService;
import bc1.gream.domain.sell.service.query.SellQueryService;
import bc1.gream.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyNowProvider {

    private final SellQueryService sellQueryService;
    private final SellCommandService sellCommandService;
    private final BuyQueryService buyQueryService;
    private final OrderCommandService orderCommandService;
    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;

    @Transactional
    public BuyNowResponseDto buyNowProduct(User buyer, BuyNowRequestDto requestDto, Long productId) {
        // 해당상품과 가격에 대한 판매입찰
        Sell sell = sellQueryService.getRecentSellBidof(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = couponQueryService.getCouponFrom(requestDto.couponId(), buyer);
        // 구매자에 대한 구매가능성 여부 검증
        BuyAvailabilityVerifier.verifyBuyerEligibility(sell.getPrice(), coupon, buyer);

        // 새로운 주문
        Order order = orderCommandService.saveOrderOf(sell, buyer, coupon);
        // 판매입찰 기프티콘에 주문 저장
        sell.getGifticon().updateOrder(order);
        // 해당 판매입찰 삭제
        sellCommandService.delete(sell);

        // 즉시구매자 포인트 감소
        buyer.decreasePoint(order.getFinalPrice());
        // 판매입찰자 포인트 증가
        order.getSeller().increasePoint(order.getExpectedPrice());

        // 매핑
        return OrderMapper.INSTANCE.toBuyNowResponseDto(order);
    }
}