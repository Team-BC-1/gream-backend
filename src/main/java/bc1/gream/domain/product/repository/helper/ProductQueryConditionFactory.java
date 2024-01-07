package bc1.gream.domain.product.repository.helper;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.math.BigInteger;

public final class ProductQueryConditionFactory {

    public static BooleanExpression brandEquals(String brand) {
        if (brand.isEmpty()) {
            return null;
        }
//        return product.brand.eq(brand);
        return null;
    }

    public static BooleanExpression nameEquals(String name) {
        if (name.isEmpty()) {
            return null;
        }
//        return product.name.eq(name);
        return null;
    }

    public static BooleanExpression hasPriceRangeOf(BigInteger startPrice, BigInteger endPrice) {
        if (startPrice.compareTo(BigInteger.ZERO) == 0) {
            return null;
        }
        if (endPrice.compareTo(BigInteger.ZERO) == 0) {
            return null;
        }
        if (startPrice.compareTo(endPrice) < 0) {
            return null;
        }
//        return product.price.between(startPrice, endPrice);
        return null;
    }
}
