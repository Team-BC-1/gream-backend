package bc1.gream.test;

import bc1.gream.domain.user.entity.Refund;

public interface RefundTest extends UserTest {

    Long TEST_REFUND_ID = 1L;

    Long TEST_REFUND_POINT = 1000L;

    String TEST_REFUND_BANK = "농협은행";

    String TEST_REFUND_ACCOUNT_NUMBER = "111-1111-1111111";

    Refund TEST_REFUND = Refund.builder()
        .point(TEST_REFUND_POINT)
        .bank(TEST_REFUND_BANK)
        .accountNumber(TEST_REFUND_ACCOUNT_NUMBER)
        .user(TEST_USER)
        .build();
}
