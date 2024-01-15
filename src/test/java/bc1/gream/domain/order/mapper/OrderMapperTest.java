package bc1.gream.domain.order.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.product.dto.response.OrderTradeResponseDto;
import bc1.gream.test.OrderTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class OrderMapperTest implements OrderTest {

    OrderMapperImpl orderMapper = new OrderMapperImpl();

    @Test
    @DisplayName("주문을 입력받아 거래반환DTO로 변환합니다.")
    public void 주문_toTradeResponseDto() {
        // WHEN
        ReflectionTestUtils.setField(TEST_ORDER, "createdAt", LocalDateTime.of(2024, 1, 1, 12, 12));
        OrderTradeResponseDto orderTradeResponseDto = orderMapper.toOrderTradeResponseDto(TEST_ORDER);

        // THEN
        assertEquals(TEST_ORDER_FINAL_PRICE, orderTradeResponseDto.finalPrice());
        assertEquals(TEST_ORDER.getCreatedAt(), orderTradeResponseDto.tradeDate());
    }
}