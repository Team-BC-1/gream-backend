package bc1.gream.test;

import static bc1.gream.domain.user.entity.CouponStatus.*;
import static bc1.gream.domain.user.entity.DiscountType.*;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.CouponStatus;
import bc1.gream.domain.user.entity.DiscountType;

public interface CouponTest extends UserTest{

    Long TEST_COUPON_ID = 1L;

    String TEST_COUPON_NAME = "TEST COUPON";

    Long TEST_DISCOUNT = 5000L;

    Long TEST_DISCOUNT_PERCENT = 10L;

    DiscountType TEST_DISCOUNT_TYPE_WON = WON;

    DiscountType TEST_DISCOUNT_TYPE_PERCENT = PERCENT;

    CouponStatus TEST_COUPON_STATUS_AVAILABLE = AVAILABLE;

    CouponStatus TEST_COUPON_STATUS_INUSE = INUSE;

    CouponStatus TEST_COUPON_STATUS_ALREADY_USED = ALREADY_USED;

    Coupon TEST_COUPON_WON = Coupon.builder()
        .name(TEST_COUPON_NAME)
        .discountType(TEST_DISCOUNT_TYPE_WON)
        .discount(TEST_DISCOUNT)
        .status(TEST_COUPON_STATUS_AVAILABLE)
        .build();

    Coupon TEST_COUPON_PERCENT = Coupon.builder()
        .name(TEST_COUPON_NAME)
        .discountType(TEST_DISCOUNT_TYPE_PERCENT)
        .discount(TEST_DISCOUNT_PERCENT)
        .status(TEST_COUPON_STATUS_AVAILABLE)
        .build();
}
