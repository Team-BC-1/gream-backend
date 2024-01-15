package bc1.gream.domain.product.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record OrderTradeResponseDto(
    Long id,
    Long finalPrice,
    LocalDateTime tradeDate
) {

}
