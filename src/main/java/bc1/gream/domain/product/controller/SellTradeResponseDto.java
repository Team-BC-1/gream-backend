package bc1.gream.domain.product.controller;

import java.time.LocalDateTime;

public record SellTradeResponseDto(
    Long sellId,
    Long sellPrice,
    LocalDateTime tradeDate
) {

}