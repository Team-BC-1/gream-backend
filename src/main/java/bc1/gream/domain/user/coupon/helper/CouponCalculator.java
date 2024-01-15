package bc1.gream.domain.user.coupon.helper;

import bc1.gream.domain.user.coupon.entity.Coupon;
import bc1.gream.domain.user.coupon.entity.DiscountType;

public final class CouponCalculator {

    public static Long calculateDiscount(Coupon coupon, Long price) {
        DiscountType discountType = coupon.getDiscountType();
        return discountType.calculateDiscount(coupon, price);
    }
}
