package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;

public interface CouponCalculatorStrategy {

    Long calculatedDiscout(Coupon coupon, Long price);
}
