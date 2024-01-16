package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.common.facade.ChangingCouponStatusFacade;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.service.SellService;
import bc1.gream.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyNowProvider {

    private final SellService sellService;
    private final CouponService couponService;
    private final OrderCommandService orderCommandService;
    private final ChangingCouponStatusFacade changingCouponStatusFacade;

    @Transactional(propagation = Propagation.REQUIRED)
    public BuyNowResponseDto buyNowProduct(User buyer, BuyNowRequestDto requestDto, Long productId) {
        Sell sell = sellService.getRecentSellBidof(productId, requestDto.price());
        Coupon coupon = couponService.findCouponById(requestDto.couponId(), buyer);
        Order order = orderCommandService.saveOrderOfSell(sell, buyer, coupon);

        sell.getGifticon().updateOrder(order);
        sellService.delete(sell);

        changingCouponStatusFacade.changeCouponStatusByCouponId(requestDto.couponId(), buyer, CouponStatus.ALREADY_USED);

        return OrderMapper.INSTANCE.toBuyNowResponseDto(order);
    }
}