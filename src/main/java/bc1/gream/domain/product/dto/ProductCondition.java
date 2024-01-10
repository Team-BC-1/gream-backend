package bc1.gream.domain.product.dto;

import lombok.Builder;

@Builder
public record ProductCondition(
    String brand,
    String name,
    Long startPrice,
    Long endPrice
) {

}