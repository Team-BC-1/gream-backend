package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;

public final class CouponCalculator {

    /**
     * CouponType 에 따라 price 에 대한 discount 진행
     *
     * @param coupon 쿠폰
     * @param price  상품 가격
     * @return 할인된 가격
     */
    public static Long calculateDiscount(Coupon coupon, Long price) {
        CouponCalculatorStrategy couponCalculatorStrategy = CouponCalculatorStrategyFactory
            .getCouponCalculatorStrategy(coupon.getDiscountType())
            .orElseThrow(() -> new GlobalException(ResultCase.COUPON_TYPE_NOT_FOUND));
        return couponCalculatorStrategy.calculateDiscount(coupon, price);
    }
}
