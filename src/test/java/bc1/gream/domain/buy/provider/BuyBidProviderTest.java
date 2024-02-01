package bc1.gream.domain.buy.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.command.BuyCommandService;
import bc1.gream.domain.buy.service.query.BuyQueryService;
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

        given(couponQueryService.checkCoupon(requestDto.couponId(), TEST_BUYER, CouponStatus.AVAILABLE)).willReturn(TEST_COUPON_FIX);
        given(buyRepository.save(any(Buy.class))).willReturn(TEST_BUY);

        // when
        BuyBidResponseDto responseDto = buyBidProvider.buyBidProduct(TEST_BUYER, requestDto, TEST_PRODUCT);

        // then
        verify(couponCommandService, times(1)).changeCouponStatus(any(Coupon.class), any(CouponStatus.class));
        verify(buyQueryService, times(1)).userPointCheck(any(User.class), any(Long.class));
        assertThat(responseDto.price()).isEqualTo(TEST_BUY_PRICE);
    }

    @Test
    void 상품_구매_입찰_추가_Provider_쿠폰_없을때_성공_테스트() {

        // given
        BuyBidRequestDto requestDto = BuyBidRequestDto.builder()
            .price(4000L)
            .period(7)
            .build();

        given(buyRepository.save(any(Buy.class))).willReturn(TEST_BUY);

        // when
        BuyBidResponseDto responseDto = buyBidProvider.buyBidProduct(TEST_BUYER, requestDto, TEST_PRODUCT);

        // then
        verify(buyQueryService, times(1)).userPointCheck(any(User.class), any(Long.class));
        assertThat(responseDto.price()).isEqualTo(TEST_BUY_PRICE);
    }

}