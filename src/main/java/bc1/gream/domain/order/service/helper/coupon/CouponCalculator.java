package bc1.gream.domain.order.service.helper.coupon;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;

public final class CouponCalculator {

    public static Long calculateDiscount(Coupon coupon, Long price) {
        CouponCalculatorStrategy couponCalculatorStrategy = CouponCalculatorStrategyFactory
            .getCouponCalculatorStrategy(coupon.getDiscountType())
            .orElseThrow(() -> new GlobalException(ResultCase.COUPON_TYPE_NOT_FOUND));
        return couponCalculatorStrategy.calculateDiscount(coupon, price);
    }
}
