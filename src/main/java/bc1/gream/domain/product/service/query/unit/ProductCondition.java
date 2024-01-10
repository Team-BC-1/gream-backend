package bc1.gream.domain.product.service.query.unit;

public record ProductCondition(
    String brand,
    String name,
    Long startPrice,
    Long endPrice
) {

}