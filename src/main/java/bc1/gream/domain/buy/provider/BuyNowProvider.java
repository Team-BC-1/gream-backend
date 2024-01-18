package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
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
    private final BuyService buyService;
    private final OrderCommandService orderCommandService;
    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;

    @Transactional
    public BuyNowResponseDto buyNowProduct(User buyer, BuyNowRequestDto requestDto, Long productId) {

        Sell sell = sellService.getRecentSellBidof(productId, requestDto.price());

        Coupon coupon = getCoupon(requestDto.couponId(), buyer);
        Order order = saveOrder(sell, buyer, coupon);
        sell.getGifticon().updateOrder(order);

        sellService.delete(sell);
        buyService.userPointCheck(buyer, order.getFinalPrice());

        buyer.decreasePoint(order.getFinalPrice());
        order.getSeller().increasePoint(order.getExpectedPrice());

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

    private Order saveOrder(Sell sell, User buyer, Coupon coupon) {
        if (coupon != null) {
            return orderCommandService.saveOrderOfSell(sell, buyer, coupon);
        }
        return orderCommandService.saveOrderOfSellNotCoupon(sell, buyer);
    }
}