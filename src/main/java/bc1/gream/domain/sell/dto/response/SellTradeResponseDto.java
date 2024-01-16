package bc1.gream.domain.sell.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SellTradeResponseDto(
    Long sellId,
    Long sellPrice,
    LocalDateTime tradeDate
) {

}