package bc1.gream.domain.sell.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.sell.dto.response.UserSellOnProgressResponseDto;
import bc1.gream.test.SellTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class SellMapperTest implements SellTest {

    SellMapperImpl sellMapper = new SellMapperImpl();

    @Test
    @DisplayName("나의 판매입찰을 진행 중인 판매입찰 결과DTO에 매핑합니다.")
    public void 판매입찰_판매입찰결과DTO에_매핑() {
        // GIVEN
        ReflectionTestUtils.setField(TEST_SELL, "id", 1L);
        ReflectionTestUtils.setField(TEST_SELL, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(TEST_SELL, "deadlineAt", LocalDateTime.now());
        ReflectionTestUtils.setField(TEST_PRODUCT, "id", 1L);
        ReflectionTestUtils.setField(TEST_GIFTICON, "id", 1L);

        // WHEN
        UserSellOnProgressResponseDto userSellOnProgressResponseDto = sellMapper.toUserSellOnProgressResponseDto(TEST_SELL);

        // THEN
        assertEquals(TEST_SELL.getId(), userSellOnProgressResponseDto.sellId());
        assertEquals(TEST_SELL.getPrice(), userSellOnProgressResponseDto.sellPrice());
        assertEquals(TEST_SELL.getCreatedAt(), userSellOnProgressResponseDto.bidStartedAt());
        assertEquals(TEST_SELL.getDeadlineAt(), userSellOnProgressResponseDto.bidDeadlineAt());
        assertEquals(TEST_PRODUCT.getId(), userSellOnProgressResponseDto.productId());
        assertEquals(TEST_PRODUCT.getBrand(), userSellOnProgressResponseDto.productBrand());
        assertEquals(TEST_PRODUCT.getName(), userSellOnProgressResponseDto.productName());
        assertEquals(TEST_GIFTICON.getId(), userSellOnProgressResponseDto.gifticonId());
        assertEquals(TEST_GIFTICON.getGifticonUrl(), userSellOnProgressResponseDto.gifticonImageUrl());
    }
}