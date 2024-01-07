package bc1.gream.domain.product.service.query.unit;

import java.math.BigInteger;

public record ProductCondition(
    String brand,
    String name,
    BigInteger startPrice,
    BigInteger endPrice
) {

}