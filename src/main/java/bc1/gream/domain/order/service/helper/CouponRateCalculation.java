package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;

public class CouponRateCalculation implements CouponCalculatorStrategy {

    @Override
    public Long calculatedDiscout(Coupon coupon, Long price) {
        return price * (100 - coupon.getDiscount()) / 100;
    }
}
