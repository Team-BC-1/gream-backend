package bc1.gream.domain.sell.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.test.ProductTest;
import bc1.gream.test.SellTest;
import bc1.gream.test.UserTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SellServiceImplTest implements UserTest, ProductTest, SellTest {

    @Mock
    SellRepository sellRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    SellServiceImpl sellService;

    @Test
    @DisplayName("즉시 판매 성공 테스트")
    void sellnow() {
        SellNowRequestDto sellNowRequestDto = SellNowRequestDto.builder()
            .price(20000L)
            .paymentType("kakao")
            .gifticonUrl("C:\\Users\\Hwnag\\Desktop\\image")
            .build();

        given(productRepository.findById(TEST_PRODUCT_ID)).willReturn(Optional.of(TEST_PRODUCT));
        given(sellRepository.save(any(Sell.class))).willReturn(TEST_SELL);

        SellNowResponseDto sellNowResponseDto = sellService.sellNowProduct(TEST_USER, sellNowRequestDto, TEST_PRODUCT_ID);

        verify(sellRepository, times(1)).save(any(Sell.class));
    }

    @Test
    @DisplayName("판매 입찰 성공 테스트")
    void sellbid() {
        SellBidRequestDto sellBidRequestDto = SellBidRequestDto.builder()
            .price(20000L)
            .paymentType("KAKAO")
            .gifticonUrl("C:\\Users\\Hwnag\\Desktop\\image")
            .period(10)
            .build();

        given(productRepository.findById(TEST_PRODUCT_ID)).willReturn(Optional.of(TEST_PRODUCT));
        given(sellRepository.save(any(Sell.class))).willReturn(TEST_SELL);

        SellBidResponseDto sellBidResponseDto = sellService.sellBidProduct(TEST_USER, sellBidRequestDto, TEST_PRODUCT_ID);

        verify(sellRepository, times(1)).save(any(Sell.class));
    }
}