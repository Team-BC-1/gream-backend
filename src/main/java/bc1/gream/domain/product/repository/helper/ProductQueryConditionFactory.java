package bc1.gream.domain.product.repository.helper;

import static bc1.gream.domain.product.entity.QProduct.product;

import com.querydsl.core.types.dsl.BooleanExpression;

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

    public static BooleanExpression hasPriceRangeOf(Long startPrice, Long endPrice) {
        if (startPrice.equals(0L) || endPrice.equals(0L)) {
            return null;
        }
        if (startPrice.compareTo(endPrice) < 0) {
            return null;
        }
        return product.price.between(startPrice, endPrice);
    }
}
