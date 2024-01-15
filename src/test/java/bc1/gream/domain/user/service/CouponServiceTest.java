package bc1.gream.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.CouponRepository;
import bc1.gream.test.CouponTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest implements CouponTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰에 대한 회원 접근권한을 검증합니다.")
    public void 쿠폰_회원접근권한_검증() {
        // GIVEN
        User mockUser = mock(User.class);
        given(mockUser.getLoginId()).willReturn(TEST_USER_LOGIN_ID);
        Coupon mockCoupon = mock(Coupon.class);
        given(mockCoupon.getUser()).willReturn(mockUser);

        // WHEN
        boolean matchCouponUser = couponService.isMatchCouponUser(mockUser, mockCoupon);

        // THEN
        assertTrue(matchCouponUser);
        verify(mockUser, times(2)).getLoginId(); // verify getLoginId() has called on mockUser
        verify(mockCoupon, times(1)).getUser(); // verify getUser() has called on mockCoupon
    }
}