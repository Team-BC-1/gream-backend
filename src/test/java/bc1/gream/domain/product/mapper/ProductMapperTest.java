package bc1.gream.domain.product.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.product.dto.response.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductQueryResponseDto;
import bc1.gream.test.ProductTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductMapperTest implements ProductTest {

    ProductMapperImpl productMapperImpl = new ProductMapperImpl();

    @Test
    @DisplayName("상품을 입력받아 ProductQueryResponseDto로 변환합니다.")
    public void 상품_toProductQueryResponseDto() {
        // WHEN
        ProductQueryResponseDto productQueryResponseDto = productMapperImpl.toQueryResponseDto(TEST_PRODUCT);

        // THEN
        assertEquals(TEST_PRODUCT_IMAGE_URL, productQueryResponseDto.imageUrl());
        assertEquals(TEST_PRODUCT_PRICE, productQueryResponseDto.price());
        assertEquals(TEST_PRODUCT_NAME, productQueryResponseDto.name());
        assertEquals(TEST_PRODUCT_DESCRIPTION, productQueryResponseDto.description());
    }

    @Test
    @DisplayName("메세지를 입력받아 ProductLikeResponseDto로 변환합니다.")
    public void 상품_toLikeResponseDto() {
        // GIVEN
        String message = "관심상품 등록";

        // WHEN
        ProductLikeResponseDto likeResponseDto = productMapperImpl.toLikeResponseDto(message);

        // THEN
        assertEquals(message, likeResponseDto.message());
    }
}