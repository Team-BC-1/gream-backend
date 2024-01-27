package bc1.gream.domain.coupon.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.entity.DiscountType;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.test.CouponTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponCommandServiceTest implements CouponTest {

    @Mock
    private CouponRepository couponRepository;
    @InjectMocks
    private CouponCommandService couponCommandService;

    @Test
    void 쿠폰상태_변경_성공() {
        // GIVEN
        Coupon mockCoupon = mock(Coupon.class);
        CouponStatus newStatus = CouponStatus.ALREADY_USED;

        // WHEN
        couponCommandService.changeCouponStatus(mockCoupon, newStatus);

        // THEN
        then(mockCoupon).should().changeStatus(newStatus);
    }

    @Test
    void 새로운_쿠폰_생성_성공() {
        // GIVEN
        AdminCreateCouponRequestDto adminCreateCouponRequestDto = new AdminCreateCouponRequestDto("쿠폰이름", DiscountType.FIX, 1000L,
            CouponStatus.AVAILABLE);
        Coupon expectedCoupon = Coupon.builder()
            .name(adminCreateCouponRequestDto.name())
            .discountType(adminCreateCouponRequestDto.discountType())
            .discount(adminCreateCouponRequestDto.discount())
            .status(adminCreateCouponRequestDto.status())
            .build();
        given(couponRepository.save(any(Coupon.class))).willReturn(expectedCoupon);

        // WHEN
        Coupon savedCoupon = couponCommandService.createCoupon(adminCreateCouponRequestDto);

        // THEN
        then(couponRepository).should(times(1)).save(any(Coupon.class));
        assertEquals(expectedCoupon, savedCoupon);
    }
}