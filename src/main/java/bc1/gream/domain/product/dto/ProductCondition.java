package bc1.gream.domain.product.dto;

public record ProductCondition(
    String brand,
    String name,
    Long startPrice,
    Long endPrice
) {

}