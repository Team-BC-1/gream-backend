package bc1.gream.domain.buy.validator;

import static bc1.gream.global.common.ResultCase.NOT_ENOUGH_POINT;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.helper.CouponCalculator;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;

public class BuyAvailabilityVerifier {

    /**
     * 입찰가와 쿠폰에 따른 최종가격에 대해 구매자의 구매가능성 여부 검증
     *
     * @param price  입찰가
     * @param coupon 쿠폰
     * @param buyer  구매자
     * @throws GlobalException when 구매자 포인트 < 할인적용된 최종가
     */
    public static void verifyBuyerEligibility(Long price, Coupon coupon, User buyer) {
        Long finalPrice = CouponCalculator.calculateDiscount(coupon, price);

        if (buyer.getPoint() < finalPrice) {
            throw new GlobalException(NOT_ENOUGH_POINT);
        }
    }
}
