package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.DiscountType;

public final class CouponCalculator {

    /**
     * CouponType 에 따라 price 에 대한 discount 진행
     *
     * @param coupon 쿠폰
     * @param price  상품 가격
     * @return 할인된 가격
     */
    public static Long calculateDiscount(Coupon coupon, Long price) {
        DiscountType discountType = coupon.getDiscountType();
        return discountType.calculateDiscount(coupon, price);
    }
}
