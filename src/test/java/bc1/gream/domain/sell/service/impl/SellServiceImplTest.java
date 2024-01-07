package bc1.gream.domain.sell.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.sell.dto.request.SellRequestDto;
import bc1.gream.domain.sell.dto.response.SellResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.GifticonRepository;
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

    @Mock
    GifticonRepository gifticonRepository;

    @InjectMocks
    SellServiceImpl sellService;

    @Test
    @DisplayName("즉시 판매 성공 테스트")
    void sellnow() {
        SellRequestDto sellRequestDto = SellRequestDto.builder()
            .price(20000L)
            .paymentType("kakao")
            .gifticonUrl("C:\\")
            .build();

        given(productRepository.findById(TEST_PRODUCT_ID)).willReturn(Optional.of(TEST_PRODUCT));
        given(sellRepository.save(any(Sell.class))).willReturn(TEST_SELL);

        SellResponseDto sellResponseDto = sellService.sellNowProduct(TEST_USER, sellRequestDto, TEST_PRODUCT_ID);

        verify(sellRepository, times(1)).save(any(Sell.class));
    }
}