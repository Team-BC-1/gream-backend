package bc1.gream.domain.coupon.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.DiscountType;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.test.CouponTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponProviderTest implements CouponTest {

    @InjectMocks
    private CouponProvider couponProvider;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CouponCommandService couponCommandService;

    @Test
    void 관리자가_사용자에게_쿠폰을_등록해주는_Provider_성공_테스트() {

        // given
        AdminCreateCouponRequestDto requestDto = AdminCreateCouponRequestDto.builder()
            .name("TEST COUPON")
            .discountType(DiscountType.FIX)
            .discount(500L)
            .userLoginId(TEST_USER_LOGIN_ID)
            .build();

        given(userRepository.findByLoginId(any(String.class))).willReturn(Optional.of(TEST_USER));
        given(couponCommandService.createCoupon(any(User.class), any(AdminCreateCouponRequestDto.class))).willReturn(TEST_COUPON_FIX);

        // when
        Coupon resultCoupon = couponProvider.createCoupon(requestDto);

        // then
        assertThat(resultCoupon.getName()).isEqualTo(requestDto.name());
        assertThat(resultCoupon.getDiscount()).isEqualTo(requestDto.discount());
        assertThat(resultCoupon.getDiscountType()).isEqualTo(requestDto.discountType());

    }
}