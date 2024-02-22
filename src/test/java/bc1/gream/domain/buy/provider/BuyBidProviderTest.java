package bc1.gream.domain.buy.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.command.BuyCommandService;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.buy.validator.BuyAvailabilityVerifier;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.BuyTest;
import bc1.gream.test.CouponTest;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuyBidProviderTest implements CouponTest, UserTest, ProductTest, BuyTest {

    @InjectMocks
    private BuyBidProvider buyBidProvider;
    @Mock
    private BuyRepository buyRepository;
    @Mock
    private BuyCommandService buyCommandService;
    @Mock
    private BuyQueryService buyQueryService;
    @Mock
    private CouponQueryService couponQueryService;
    @Mock
    private CouponCommandService couponCommandService;

    @Test
    @DisplayName("구매입찰 Provider의 기능 중 상품 구매 입찰신청 기능 성공 테스트")
    void 상품_구매_입찰신청_성공_테스트() {
        // given
        BuyBidRequestDto requestDto = BuyBidRequestDto.builder()
            .price(4000L)
            .couponId(1L)
            .period(7)
            .build();

        given(couponQueryService.getCouponFrom(requestDto.couponId(), TEST_BUYER)).willReturn(TEST_COUPON_FIX);
        given(buyRepository.save(any(Buy.class))).willReturn(TEST_BUY);

        try (MockedStatic<BuyAvailabilityVerifier> mockedStatic = mockStatic(BuyAvailabilityVerifier.class)) {
            // when
            BuyBidResponseDto responseDto = buyBidProvider.buyBidProduct(TEST_BUYER, requestDto, TEST_PRODUCT);

            // then
            mockedStatic.verify(
                () -> BuyAvailabilityVerifier.verifyBuyerEligibility(eq(requestDto.price()), nullable(Coupon.class), any(User.class)));
            assertThat(responseDto.price()).isEqualTo(TEST_BUY_PRICE);
        }
    }


    @Test
    void 상품_구매_입찰_추가_Provider_쿠폰_없을때_성공_테스트() {

        // given
        BuyBidRequestDto requestDto = BuyBidRequestDto.builder()
            .price(4000L)
            .period(7)
            .build();

        given(buyRepository.save(any(Buy.class))).willReturn(TEST_BUY);

        try (MockedStatic<BuyAvailabilityVerifier> mockedStatic = mockStatic(BuyAvailabilityVerifier.class)) {
            // when
            BuyBidResponseDto responseDto = buyBidProvider.buyBidProduct(TEST_BUYER, requestDto, TEST_PRODUCT);

            // then
            mockedStatic.verify(
                () -> BuyAvailabilityVerifier.verifyBuyerEligibility(eq(requestDto.price()), nullable(Coupon.class), any(User.class)));
            assertThat(responseDto.price()).isEqualTo(TEST_BUY_PRICE);
        }
    }

    @Test
    void 구매_입찰취소_쿠폰_없을때_Provider_성공_테스트() {

        // given
        given(buyQueryService.findBuyById(any(Long.class))).willReturn(TEST_BUY);

        // when
        BuyCancelBidResponseDto responseDto = buyBidProvider.buyCancelBid(TEST_USER, 1L);

        // then
        assertThat(responseDto.buyId()).isEqualTo(1L);
        verify(buyCommandService, times(1)).deleteBuyByIdAndUser(any(Buy.class), any(User.class));
    }

    @Test
    void 구매_입찰취소_쿠폰_있을때_Provider_성공_테스트() {

        // given
        given(buyQueryService.findBuyById(any(Long.class))).willReturn(TEST_BUY_COUPON);
        given(couponQueryService.checkCoupon(any(Long.class), any(User.class), any(CouponStatus.class))).willReturn(TEST_COUPON_FIX);

        // when
        BuyCancelBidResponseDto responseDto = buyBidProvider.buyCancelBid(TEST_USER, 1L);

        // then
        assertThat(responseDto.buyId()).isEqualTo(1L);
        verify(buyCommandService, times(1)).deleteBuyByIdAndUser(any(Buy.class), any(User.class));
        verify(couponCommandService, times(1)).changeCouponStatus(any(Coupon.class), any(CouponStatus.class));
    }
}