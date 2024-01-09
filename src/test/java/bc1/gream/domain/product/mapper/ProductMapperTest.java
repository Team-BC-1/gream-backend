package bc1.gream.domain.product.mapper;

import bc1.gream.domain.product.dto.ProductQueryResponseDto;
import bc1.gream.test.OrderTest;
import bc1.gream.test.ProductTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductMapperTest implements ProductTest, OrderTest {

    @Autowired
    ProductMapperImpl productMapperImpl;

    @Test
    @DisplayName("상품을 입력받아 ProductQueryResponseDto로 변환합니다.")
    public void 상품_toProductQueryResponseDto() {
        // GIVEN

        // WHEN
        ProductQueryResponseDto productQueryResponseDto = productMapperImpl.toQueryResponseDto(TEST_PRODUCT);

        // THEN
        System.out.println("productQueryResponseDto = " + productQueryResponseDto);
    }

    @Test
    @DisplayName("메세지를 입력받아 ProductLikeResponseDto로 변환합니다.")
    public void 상품_toLikeResponseDto() {
        // GIVEN

        // WHEN

        // THEN

    }

    @Test
    @DisplayName("주문을 입력받아 TradeResponseDto로 변환합니다.")
    public void 상품_toTradeResponseDto() {
        // GIVEN

        // WHEN

        // THEN

    }
}