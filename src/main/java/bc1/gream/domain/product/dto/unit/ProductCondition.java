package bc1.gream.domain.product.dto.unit;

import lombok.Builder;

@Builder
public record ProductCondition(
    String brand,
    String name,
    Long startPrice,
    Long endPrice
) {

}