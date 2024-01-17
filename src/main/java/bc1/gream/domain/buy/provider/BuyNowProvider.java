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
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
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
        Coupon coupon;
        Order order;

        Sell sell = sellService.getRecentSellBidof(productId, requestDto.price());

        if (requestDto.couponId() != null) {
            coupon = couponQueryService.checkCoupon(requestDto.couponId(), buyer, CouponStatus.AVAILABLE);
            couponCommandService.changeCouponStatus(coupon, CouponStatus.ALREADY_USED);
            order = orderCommandService.saveOrderOfSell(sell, buyer, coupon);
        } else {
            order = orderCommandService.saveOrderOfSellNotCoupon(sell, buyer);
        }

        sell.getGifticon().updateOrder(order);
        sellService.delete(sell);

        if (!buyService.userPointCheck(buyer, order.getFinalPrice())) {
            throw new GlobalException(ResultCase.NOT_ENOUGH_POINT);
        }

        buyer.decreasePoint(order.getFinalPrice());

        return OrderMapper.INSTANCE.toBuyNowResponseDto(order);
    }
}