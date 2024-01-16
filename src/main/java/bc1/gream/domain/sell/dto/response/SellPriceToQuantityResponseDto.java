package bc1.gream.domain.sell.dto.response;

import lombok.Builder;

@Builder
public record SellPriceToQuantityResponseDto(
    Long sellPrice,
    Long quantity
) {

}
