package bc1.gream.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.order.dto.request.SellBidRequestDto;
import bc1.gream.domain.order.dto.response.SellBidResponseDto;
import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.repository.SellRepository;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.test.SellTest;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class SellServiceTest implements SellTest {

    @Mock
    SellRepository sellRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    SellService sellService;

    @Test
    void sellBidProductTest() throws IOException {

        // given
        String url = "images/images.png";
        ClassPathResource fileResource = new ClassPathResource(url);

        SellBidRequestDto requestDto = SellBidRequestDto.builder()
            .price(TEST_SELL_PRICE)
            .gifticonUrl(fileResource.getURL().getPath())
            .build();

        given(productRepository.findById(1L)).willReturn(Optional.of(TEST_PRODUCT));
        given(sellRepository.save(any(Sell.class))).willReturn(TEST_SELL);

        // when
        SellBidResponseDto responseDto = sellService.sellBidProduct(TEST_USER, requestDto, TEST_PRODUCT_ID);

        // then
        assertThat(responseDto.price()).isEqualTo(TEST_SELL_PRICE);
    }

    @Test
    void sellCancelBidTest() {

        // given
        given(sellRepository.findById(TEST_SELL_ID)).willReturn(Optional.of(TEST_SELL));

        // when
        sellService.sellCancelBid(TEST_USER, TEST_SELL_ID);

        // then
        verify(sellRepository, times(1)).delete(any(Sell.class));
    }
}