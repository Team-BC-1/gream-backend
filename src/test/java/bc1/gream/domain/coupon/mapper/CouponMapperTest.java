package bc1.gream.domain.coupon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.admin.dto.response.AdminCreateCouponResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.test.CouponTest;
import org.junit.jupiter.api.Test;

class CouponMapperTest implements CouponTest {

    @Test
    void 어드민_쿠폰생성결과_매핑() {
        // GIVEN
        Coupon coupon = TEST_COUPON_FIX;

        // WHEN
        AdminCreateCouponResponseDto responseDto = CouponMapper.INSTANCE.toAdminCreateCouponResponseDto(coupon);

        // THEN
        assertEquals("AdminCreateCouponResponseDto[]", responseDto.toString());
    }
}