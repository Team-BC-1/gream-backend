package bc1.gream.test;

import static bc1.gream.domain.user.coupon.entity.CouponStatus.ALREADY_USED;
import static bc1.gream.domain.user.coupon.entity.CouponStatus.AVAILABLE;
import static bc1.gream.domain.user.coupon.entity.CouponStatus.IN_USE;
import static bc1.gream.domain.user.coupon.entity.DiscountType.FIX;
import static bc1.gream.domain.user.coupon.entity.DiscountType.RATE;

import bc1.gream.domain.user.coupon.entity.Coupon;
import bc1.gream.domain.user.coupon.entity.CouponStatus;
import bc1.gream.domain.user.coupon.entity.DiscountType;

public interface CouponTest extends UserTest {

    Long TEST_COUPON_ID = 1L;

    String TEST_COUPON_NAME = "TEST COUPON";

    Long TEST_DISCOUNT = 5000L;

    Long TEST_DISCOUNT_PERCENT = 10L;

    DiscountType TEST_DISCOUNT_TYPE_WON = FIX;

    DiscountType TEST_DISCOUNT_TYPE_PERCENT = RATE;

    CouponStatus TEST_COUPON_STATUS_AVAILABLE = AVAILABLE;

    CouponStatus TEST_COUPON_STATUS_INUSE = IN_USE;

    CouponStatus TEST_COUPON_STATUS_ALREADY_USED = ALREADY_USED;

    Coupon TEST_COUPON_FIX = Coupon.builder()
        .name(TEST_COUPON_NAME)
        .discountType(TEST_DISCOUNT_TYPE_WON)
        .discount(TEST_DISCOUNT)
        .status(TEST_COUPON_STATUS_AVAILABLE)
        .build();

    Coupon TEST_COUPON_RATE = Coupon.builder()
        .name(TEST_COUPON_NAME)
        .discountType(TEST_DISCOUNT_TYPE_PERCENT)
        .discount(TEST_DISCOUNT_PERCENT)
        .status(TEST_COUPON_STATUS_AVAILABLE)
        .build();
}
