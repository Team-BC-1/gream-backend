package bc1.gream.domain.sell.service;

import static bc1.gream.test.SellTest.TEST_SELL;
import static bc1.gream.test.SellTest.TEST_SELL_ID;
import static bc1.gream.test.SellTest.TEST_SELL_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.gifticon.service.command.GifticonCommandService;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.provider.SellBidProvider;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.GifticonTest;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class SellBidProviderTest implements GifticonTest {

    @Mock
    SellRepository sellRepository;
    @Mock
    GifticonCommandService gifticonCommandService;
    @Mock
    SellService sellService;

    @InjectMocks
    SellBidProvider sellBidProvider;

    @Test
    void sellBidProductTest() throws IOException {

        // given
        String url = "images/images.png";
        ClassPathResource fileResource = new ClassPathResource(url);

        SellBidRequestDto requestDto = SellBidRequestDto.builder()
            .price(TEST_SELL_PRICE)
            .gifticonUrl(fileResource.getURL().getPath())
            .build();

        given(gifticonCommandService.saveGifticon(requestDto.gifticonUrl(), null)).willReturn(TEST_GIFTICON);
        given(sellRepository.save(any(Sell.class))).willReturn(TEST_SELL);

        // when
        SellBidResponseDto responseDto = sellBidProvider.createSellBid(TEST_USER, requestDto, TEST_PRODUCT);

        // then
        assertThat(responseDto.price()).isEqualTo(TEST_SELL_PRICE);
    }

    @Test
    void sellCancelBidTest() {
        // given
        given(sellService.deleteSellByIdAndUser(TEST_SELL_ID, TEST_USER)).willReturn(TEST_SELL);

        // when
        sellBidProvider.sellCancelBid(TEST_USER, TEST_SELL_ID);

        // then
        verify(sellService, times(1)).deleteSellByIdAndUser(any(Long.class), any(User.class));
//        verify(gifticonRepository, times(1)).delete(any(Gifticon.class));
    }
}