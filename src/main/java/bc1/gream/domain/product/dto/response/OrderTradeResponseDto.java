package bc1.gream.domain.product.dto.response;

import java.time.LocalDateTime;

public record OrderTradeResponseDto(
    Long id,
    Long finalPrice,
    LocalDateTime tradeDate
) {

}
