package bc1.gream.domain.coupon.service.qeury;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.security.UserDetailsImpl;
import bc1.gream.test.CouponTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponQueryServiceTest implements CouponTest {

    @InjectMocks
    private CouponQueryService couponQueryService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void 쿠폰이_상태가_원하는_상태인지_체크하는_서비스_기능_성공_테스트() {

        // given
        given(couponRepository.findById(any(Long.class))).willReturn(Optional.of(TEST_COUPON_OF_TEST_USER));

        // when
        Coupon coupon = couponQueryService.checkCoupon(TEST_COUPON_ID, TEST_USER, CouponStatus.AVAILABLE);

        // then
        assertThat(coupon.getName()).isEqualTo(TEST_COUPON_OF_TEST_USER.getName());
        assertThat(coupon.getDiscount()).isEqualTo(TEST_COUPON_OF_TEST_USER.getDiscount());
        assertThat(coupon.getDiscountType()).isEqualTo(TEST_COUPON_OF_TEST_USER.getDiscountType());
    }

    @Test
    void 쿠폰이_상태가_원하는_상태인지_체크하는_서비스_기능_쿠폰이_존재하지_않으므로_인한_테스트() {

        // given
        given(couponRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            couponQueryService.checkCoupon(TEST_COUPON_ID, TEST_BUYER, CouponStatus.AVAILABLE);
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.COUPON_NOT_FOUND);
        assertThat(exception.getResultCase().getCode()).isEqualTo(ResultCase.COUPON_NOT_FOUND.getCode());
        assertThat(exception.getResultCase().getMessage()).isEqualTo(ResultCase.COUPON_NOT_FOUND.getMessage());
    }

    @Test
    void 쿠폰이_상태가_원하는_상태인지_체크하는_서비스_기능_로그인한_유저의_쿠폰이_아님으로_인한_실패_테스트() {

        // given
        given(couponRepository.findById(any(Long.class))).willReturn(Optional.of(TEST_COUPON_OF_TEST_USER));

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            couponQueryService.checkCoupon(TEST_COUPON_ID, TEST_BUYER, CouponStatus.AVAILABLE);
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.NOT_AUTHORIZED);
        assertThat(exception.getResultCase().getCode()).isEqualTo(ResultCase.NOT_AUTHORIZED.getCode());
        assertThat(exception.getResultCase().getMessage()).isEqualTo(ResultCase.NOT_AUTHORIZED.getMessage());
    }

    @Test
    void 쿠폰이_사용가능한지_체크하는_서비스_기능_이미_사용한_쿠폰으로_인한_실패_테스트() {

        // given
        given(couponRepository.findById(any(Long.class))).willReturn(Optional.of(TEST_COUPON_OF_TEST_USER));

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            couponQueryService.checkCoupon(TEST_COUPON_ID, TEST_USER, CouponStatus.ALREADY_USED);
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.COUPON_STATUS_CHANGE_FAIL);
        assertThat(exception.getResultCase().getCode()).isEqualTo(ResultCase.COUPON_STATUS_CHANGE_FAIL.getCode());
        assertThat(exception.getResultCase().getMessage()).isEqualTo(ResultCase.COUPON_STATUS_CHANGE_FAIL.getMessage());
    }

    @Test
    void 사용_가능한_유저의_쿠폰_리스트_조회_성공_테스트() {

        // given
        List<Coupon> couponList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            couponList.add(TEST_COUPON_FIX);
        }

        given(couponRepository.availableCoupon(any(User.class))).willReturn(couponList);
        UserDetailsImpl userDetails = new UserDetailsImpl(TEST_USER);

        // when
        List<Coupon> resultList = couponQueryService.availableCouponList(userDetails);

        // then
        assertThat(resultList.get(0)).isEqualTo(couponList.get(0));
        assertThat(resultList.get(1)).isEqualTo(couponList.get(1));
        assertThat(resultList.get(2)).isEqualTo(couponList.get(2));
    }

    @Test
    void unavailableCouponList() {
    }

    @Test
    void findCouponById() {
    }

    @Test
    @DisplayName("쿠폰에 대한 회원 접근권한을 검증합니다.")
    public void 쿠폰_회원접근권한_검증() {
        // GIVEN
        User mockUser = mock(User.class);
        given(mockUser.getLoginId()).willReturn(TEST_USER_LOGIN_ID);
        Coupon mockCoupon = mock(Coupon.class);
        given(mockCoupon.getUser()).willReturn(mockUser);

        // WHEN
        boolean matchCouponUser = couponQueryService.isMatchCouponUser(mockUser, mockCoupon);

        // THEN
        assertTrue(matchCouponUser);
        verify(mockUser, times(2)).getLoginId(); // verify getLoginId() has called on mockUser
        verify(mockCoupon, times(1)).getUser(); // verify getUser() has called on mockCoupon
    }
}