package bc1.gream.domain.buy.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.buy.validator.BuyAvailabilityVerifier;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.service.command.SellCommandService;
import bc1.gream.domain.sell.service.query.SellQueryService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.CouponTest;
import bc1.gream.test.OrderTest;
import bc1.gream.test.SellTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuyNowProviderTest implements SellTest, CouponTest, OrderTest {

    @InjectMocks
    private BuyNowProvider buyNowProvider;
    @Mock
    private SellQueryService sellQueryService;
    @Mock
    private SellCommandService sellCommandService;
    @Mock
    private BuyQueryService buyQueryService;
    @Mock
    private OrderCommandService orderCommandService;
    @Mock
    private CouponQueryService couponQueryService;
    @Mock
    private CouponCommandService couponCommandService;

    @Test
    void 판매입찰_되어있는_상품_쿠폰_있을시_즉시_구매하는_Provider_기능_성공_테스트() {
        // given
        BuyNowRequestDto requestDto = BuyNowRequestDto.builder()
            .price(TEST_SELL_PRICE)
            .couponId(1L)
            .build();

        given(sellQueryService.getRecentSellBidof(any(Long.class), any(Long.class))).willReturn(TEST_SELL);
        given(couponQueryService.getCouponFrom(anyLong(), any(User.class))).willReturn(TEST_COUPON_FIX);
        given(orderCommandService.saveOrderOf(any(Sell.class), any(User.class), any(Coupon.class))).willReturn(TEST_ORDER);

        try (MockedStatic<BuyAvailabilityVerifier> mockedStatic = mockStatic(BuyAvailabilityVerifier.class)) {
            // when
            BuyNowResponseDto responseDto = buyNowProvider.buyNowProduct(TEST_BUYER, requestDto, TEST_PRODUCT_ID);

            // then
            assertThat(responseDto.finalPrice()).isEqualTo(TEST_SELL_PRICE - TEST_DISCOUNT);
            assertThat(responseDto.expectedPrice()).isEqualTo(TEST_SELL_PRICE);
            mockedStatic.verify(() -> BuyAvailabilityVerifier.verifyBuyerEligibility(anyLong(), any(Coupon.class), any(User.class)));
            verify(sellCommandService, times(1)).delete(any(Sell.class));
        }
    }

    @Test
    void 판매입찰_되어있는_상품_쿠폰_없을시_즉시_구매하는_Provider_기능_성공_테스트() {
        // given
        BuyNowRequestDto requestDto = BuyNowRequestDto.builder()
            .price(TEST_SELL_PRICE)
            .build();

        given(sellQueryService.getRecentSellBidof(any(Long.class), any(Long.class))).willReturn(TEST_SELL);
        given(orderCommandService.saveOrderOf(any(Sell.class), any(User.class), nullable(Coupon.class))).willReturn(TEST_ORDER_NOT_COUPON);

        try (MockedStatic<BuyAvailabilityVerifier> mockedStatic = mockStatic(BuyAvailabilityVerifier.class)) {
            // when
            BuyNowResponseDto responseDto = buyNowProvider.buyNowProduct(TEST_BUYER, requestDto, TEST_PRODUCT_ID);

            // then
            assertThat(responseDto.finalPrice()).isEqualTo(TEST_SELL_PRICE);
            assertThat(responseDto.expectedPrice()).isEqualTo(TEST_SELL_PRICE);
            mockedStatic.verify(() -> BuyAvailabilityVerifier.verifyBuyerEligibility(anyLong(), nullable(Coupon.class), any(User.class)));
            verify(sellCommandService, times(1)).delete(any(Sell.class));
        }
    }
}