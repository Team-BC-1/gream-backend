package bc1.gream.domain.sell.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.SellTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SellServiceTest implements SellTest {

    @Mock
    SellRepository sellRepository;

    @InjectMocks
    SellService sellService;

    @Test
    void sellBidProductTest() {

        // given
        Product product = TEST_PRODUCT;
        User user = TEST_USER;
        SellBidRequestDto requestDto = SellBidRequestDto.builder()
            .price(TEST_SELL_PRICE)
            .gifticonUrl("C:\\Users\\Hwnag\\Desktop\\image")
            .build();

        given(sellRepository.save(any(Sell.class))).willReturn(TEST_SELL);

        // when
        SellBidResponseDto responseDto = sellService.sellBidProduct(user, requestDto, product);

        // then
        assertThat(responseDto.price()).isEqualTo(TEST_SELL_PRICE);
    }

}