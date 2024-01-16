package bc1.gream.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
import bc1.gream.test.BuyTest;
import bc1.gream.test.CouponTest;
import bc1.gream.test.SellTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BuyServiceTest implements BuyTest, SellTest, CouponTest {

    @Mock
    BuyRepository buyRepository;
    @Mock
    SellRepository sellRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    GifticonRepository gifticonRepository;


    @Mock
    ProductService productService;
    @Mock
    CouponService couponService;

    @InjectMocks
    BuyService buyService;

    @Test
    void buyBidProductTest() {

        // given
        BuyBidRequestDto requestDto = BuyBidRequestDto.builder()
            .price(TEST_BUY_PRICE)
            .build();

        given(productService.findBy(any())).willReturn(TEST_PRODUCT);
        given(buyRepository.save(any(Buy.class))).willReturn(TEST_BUY);

        // when
        BuyBidResponseDto responseDto = buyService.buyBidProduct(TEST_USER, requestDto, TEST_PRODUCT_ID);

        // then
        assertThat(responseDto.price()).isEqualTo(TEST_BUY_PRICE);
    }

    @Test
    void buyCancelBidTest() {

        // given
        given(buyRepository.findById(TEST_BUY_ID)).willReturn(Optional.of(TEST_BUY));

        // when
        buyService.buyCancelBid(TEST_USER, TEST_BUY_ID);

        // then
        verify(buyRepository, times(1)).delete(any(Buy.class));
    }

    @Test
    void buyNowProductTest() {
        BuyNowRequestDto requestDto = BuyNowRequestDto.builder()
            .price(TEST_BUY_PRICE)
            .couponId(TEST_COUPON_ID)
            .build();
        ReflectionTestUtils.setField(TEST_SELL, "id", 1L);
        Long price = requestDto.price();
        given(sellRepository.findByProductIdAndPrice(TEST_PRODUCT_ID, price)).willReturn(Optional.of(TEST_SELL));
        given(couponService.findCouponById(any(Long.class), any(User.class))).willReturn(TEST_COUPON_FIX);
        given(orderRepository.save(any(Order.class))).willReturn(TEST_ORDER);

        BuyNowResponseDto responseDto = buyService.buyNowProduct(TEST_BUYER, requestDto, TEST_PRODUCT_ID);

        assertThat(responseDto.finalPrice()).isEqualTo(TEST_ORDER_FINAL_PRICE);
        assertThat(responseDto.expectedPrice()).isEqualTo(TEST_ORDER_EXPECTED_PRICE);
    }

    @Test
    void findBuyByIdTest() {

        // given
        given(buyRepository.findById(TEST_BUY_ID)).willReturn(Optional.of(TEST_BUY));
        // when
        Buy buy = buyService.findBuyById(TEST_BUY_ID);
        // then
        assertThat(buy.getPrice()).isEqualTo(TEST_BUY.getPrice());
        assertThat(buy.getDeadlineAt()).isEqualTo(TEST_BUY.getDeadlineAt());
    }
}