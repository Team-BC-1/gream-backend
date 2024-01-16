package bc1.gream.domain.gifticon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.sell.dto.response.UserSoldGifticonResponseDto;
import bc1.gream.test.GifticonTest;
import org.junit.jupiter.api.Test;

class GifticonMapperTest implements GifticonTest {

    @Test
    public void 사용자가판매한_기프티콘Dto_매핑() {
        // WHEN
        UserSoldGifticonResponseDto responseDto = GifticonMapper.INSTANCE.toUserBoughtGifticonResponseDto(TEST_GIFTICON_END);

        // THEN
        assertEquals(TEST_GIFTICON_END.getOrder().getProduct().getBrand(), responseDto.brand());
        assertEquals(TEST_GIFTICON_END.getOrder().getProduct().getName(), responseDto.name());
        assertEquals(TEST_GIFTICON_END.getOrder().getProduct().getDescription(), responseDto.description());
    }
}