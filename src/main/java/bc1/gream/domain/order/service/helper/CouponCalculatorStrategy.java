package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;

public interface CouponCalculatorStrategy {

    /**
     * 쿠폰에 따른 할인 수행
     *
     * @param coupon 쿠폰
     * @param price  상품가격
     * @return 할인가격
     */
    Long calculateDiscount(Coupon coupon, Long price);
}
