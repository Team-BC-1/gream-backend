package bc1.gream.test;

import bc1.gream.domain.product.entity.Product;

public interface ProductTest {
    Long TEST_PRODUCT_ID = 1L;

    Product TEST_PRODUCT = Product.builder().build();
}
