package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;

public class CouponFixedCalculation implements CouponCalculatorStrategy {

    /**
     * 고정 가격 할인 수행
     *
     * @param coupon 쿠폰
     * @param price  상품가격
     * @return 할인가
     */
    @Override
    public Long calculateDiscount(Coupon coupon, Long price) {
        return price - coupon.getDiscount();
    }
}
