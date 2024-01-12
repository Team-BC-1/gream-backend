package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;

public class CouponFixedCalculation implements CouponCalculatorStrategy {

    @Override
    public Long calculateDiscount(Coupon coupon, Long price) {
        return price - coupon.getDiscount();
    }
}
