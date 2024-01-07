package bc1.gream.test;

import static java.time.LocalDateTime.now;

import bc1.gream.domain.sell.entity.Sell;
import java.time.LocalDateTime;

public interface SellTest extends ProductTest, UserTest {

    Long TEST_SELL_ID = 1L;

    Long TEST_PRICE = 20000L;

    Boolean TEST_IS_CLOSE = true;

    LocalDateTime TEST_DEADLINE_AT = now().plusDays(7);

    Boolean TEST_IS_NOW = true;

    String TEST_PAYMENT_TYPE = "KAKAO";

    Sell TEST_SELL = Sell.builder()
        .price(TEST_PRICE)
        .isClose(TEST_IS_CLOSE)
        .deadlineAt(TEST_DEADLINE_AT)
        .isNow(TEST_IS_NOW)
        .paymentType(TEST_PAYMENT_TYPE)
        .user(TEST_USER)
        .product(TEST_PRODUCT)
        .build();
}
