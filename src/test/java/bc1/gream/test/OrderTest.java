package bc1.gream.test;

import bc1.gream.domain.order.entity.Order;
import java.time.LocalDateTime;

public interface OrderTest extends UserTest, ProductTest {

    Long TEST_ORDER_ID = 1L;
    Long TEST_ORDER_FINAL_PRICE = 4_500L;
    Long TEST_ORDER_SALE_PURCHASED_PRICE = 4_000L;
    LocalDateTime TEST_ORDER_ORDERED_AT = LocalDateTime.of(2024, 1, 1, 12, 12);

    Order TEST_ORDER = Order.builder()
        .product(TEST_PRODUCT)
        .buyer(TEST_BUYER)
        .seller(TEST_SELLER)
        .id(TEST_ORDER_ID)
        .finalPurchasePrice(TEST_ORDER_FINAL_PRICE)
        .expectedPurchasedPrice(TEST_ORDER_SALE_PURCHASED_PRICE)
        .orderedAt(TEST_ORDER_ORDERED_AT)
        .build();
}
