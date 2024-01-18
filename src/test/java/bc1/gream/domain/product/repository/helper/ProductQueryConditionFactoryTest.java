package bc1.gream.domain.product.repository.helper;

import static bc1.gream.domain.product.entity.QProduct.product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import bc1.gream.test.ProductTest;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;

class ProductQueryConditionFactoryTest implements ProductTest {

    @Test
    public void null에대해서_동적쿼리생성하지않음() {
        // WHEN
        assertNull(ProductQueryConditionFactory.brandEquals(""));
        assertNull(ProductQueryConditionFactory.nameEquals(""));
        assertNull(ProductQueryConditionFactory.hasPriceRangeOf(0L, 0L));
    }

    @Test
    public void brand에대한_동적쿼리() {
        // WHEN
        BooleanExpression expression = ProductQueryConditionFactory.brandEquals(TEST_PRODUCT_BRAND);

        // THEN
        assertEquals(product.brand.eq(TEST_PRODUCT_BRAND), expression);
    }

    @Test
    public void name에대한_동적쿼리() {
        BooleanExpression expression = ProductQueryConditionFactory.nameEquals(TEST_PRODUCT_NAME);

        // THEN
        assertEquals(product.name.toLowerCase().containsIgnoreCase(TEST_PRODUCT_NAME), expression);
    }
    // WHEN

    @Test
    public void startPrice_endPrice에대한_동적쿼리() {
        // GIVEN
        Long startPrice = 4_500L;
        Long endPrice = 5_000L;

        // WHEN
        BooleanExpression expression = ProductQueryConditionFactory.hasPriceRangeOf(startPrice, endPrice);

        // THEN
        assertEquals(product.price.between(startPrice, endPrice), expression);
    }

    @Test
    public void startPrice가_endPrice보다높다면_동적쿼리_생성하지않음() {
        // GIVEN
        Long startPrice = 5_000L;
        Long endPrice = 4_500L;

        // WHEN
        BooleanExpression expression = ProductQueryConditionFactory.hasPriceRangeOf(startPrice, endPrice);

        // THEN
        assertNull(expression);
    }
}