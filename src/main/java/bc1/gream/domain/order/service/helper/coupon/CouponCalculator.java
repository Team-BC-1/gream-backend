package bc1.gream.domain.order.service.helper.coupon;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.DiscountType;

public final class CouponCalculator {

    public static Long calculateDiscount(Coupon coupon, Long price) {
        DiscountType discountType = coupon.getDiscountType();
        return discountType.calculateDiscount(coupon, price);
    }
}
