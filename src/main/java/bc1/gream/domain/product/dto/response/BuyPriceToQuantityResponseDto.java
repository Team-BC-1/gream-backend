package bc1.gream.domain.product.dto.response;

import lombok.Builder;

@Builder
public record BuyPriceToQuantityResponseDto(
    Long buyPrice,
    Long quantity
) {

}