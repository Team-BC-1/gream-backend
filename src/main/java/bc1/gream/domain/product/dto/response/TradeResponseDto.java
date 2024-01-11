package bc1.gream.domain.product.dto.response;

import java.time.LocalDateTime;

public record TradeResponseDto(
    Long price,
    LocalDateTime tradeDate
) {

}
