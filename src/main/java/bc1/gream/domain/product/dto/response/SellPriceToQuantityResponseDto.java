package bc1.gream.domain.product.dto.response;

import lombok.Builder;

@Builder
public record SellPriceToQuantityResponseDto(
    Long sellPrice,
    Long sellQuantity
) {

}
