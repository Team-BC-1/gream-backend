package bc1.gream.domain.order.service.helper.coupon;

import bc1.gream.domain.user.entity.Coupon;

public interface CouponCalculatorStrategy {

    Long calculateDiscount(Coupon coupon, Long price);
}
