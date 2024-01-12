package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.Coupon;

public class CouponRateCalculation implements CouponCalculatorStrategy {

    /**
     * 할인율에 따른 할인수행
     *
     * @param coupon 쿠폰
     * @param price  상품가격
     * @return 할인가
     */
    @Override
    public Long calculateDiscount(Coupon coupon, Long price) {
        return price * (100 - coupon.getDiscount()) / 100;
    }
}
