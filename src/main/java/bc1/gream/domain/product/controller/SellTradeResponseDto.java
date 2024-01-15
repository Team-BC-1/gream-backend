package bc1.gream.domain.product.controller;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SellTradeResponseDto(
    Long sellId,
    Long sellPrice,
    LocalDateTime tradeDate
) {

}