package bc1.gream.test;

import bc1.gream.domain.buy.entity.Buy;
import java.time.LocalDateTime;

public interface BuyTest extends UserTest, ProductTest {

    Long TEST_BUY_ID = 1L;

    Long TEST_BUY_PRICE = 4_500L;

    LocalDateTime TEST_DEADLINE_AT = LocalDateTime.now().plusDays(7);

    Buy TEST_BUY = Buy.builder()
        .price(TEST_BUY_PRICE)
        .deadlineAt(TEST_DEADLINE_AT)
        .user(TEST_USER)
        .product(TEST_PRODUCT)
        .build();
}
