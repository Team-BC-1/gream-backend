package bc1.gream.test;

import bc1.gream.domain.order.entity.Order;

public interface OrderTest extends UserTest, ProductTest {

    Long TEST_ORDER_ID = 1L;
    Long TEST_ORDER_FINAL_PRICE = 4_000L;
    Long TEST_ORDER_EXPECTED_PRICE = 4_500L;

    Order TEST_ORDER = Order.builder()
        .product(TEST_PRODUCT)
        .buyer(TEST_BUYER)
        .seller(TEST_SELLER)
        .finalPrice(TEST_ORDER_FINAL_PRICE)
        .expectedPrice(TEST_ORDER_EXPECTED_PRICE)
        .build();
}
