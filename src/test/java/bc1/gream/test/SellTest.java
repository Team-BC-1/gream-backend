package bc1.gream.test;

import bc1.gream.domain.order.entity.Sell;
import java.time.LocalDateTime;

public interface SellTest extends UserTest, ProductTest {

    Long TEST_SELL_ID = 1L;

    Long TEST_SELL_PRICE = 4_500L;

    LocalDateTime TEST_DEADLINE_AT = LocalDateTime.now().plusDays(7);

    String TEST_GIFTICON_URL = "images/images.png";

    Sell TEST_SELL = Sell.builder()
        .price(TEST_SELL_PRICE)
        .deadlineAt(TEST_DEADLINE_AT)
        .product(TEST_PRODUCT)
        .user(TEST_USER)
        .build();
}
