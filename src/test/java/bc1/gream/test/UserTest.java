package bc1.gream.test;

import bc1.gream.domain.user.entity.Provider;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.entity.UserRole;

public interface UserTest {

    Long TEST_USER_ID = 1L;
    String TEST_USER_LOGIN_ID = "loginId01";
    String TEST_USER_NICKNAME = "nickname01";
    String TEST_USER_PASSWORD = "ABcd5678#&";

    User TEST_USER = User.builder()
        .loginId(TEST_USER_LOGIN_ID)
        .nickname(TEST_USER_NICKNAME)
        .password(TEST_USER_PASSWORD)
        .role(UserRole.USER)
        .provider(Provider.LOCAL)
        .build();

    Long TEST_BUYER_ID = 2L;
    String TEST_BUYER_LOGIN_ID = "buyerId01";
    String TEST_BUYER_NICKNAME = "buyerNickname01";
    String TEST_BUYER_PASSWORD = "ABcd5678#&";

    User TEST_BUYER = User.builder()
        .loginId(TEST_BUYER_LOGIN_ID)
        .nickname(TEST_BUYER_NICKNAME)
        .password(TEST_BUYER_PASSWORD)
        .role(UserRole.USER)
        .provider(Provider.LOCAL)
        .build();

    Long TEST_SELLER_ID = 3L;
    String TEST_SELLER_LOGIN_ID = "sellerId01";
    String TEST_SELLER_NICKNAME = "sellerNickname01";
    String TEST_SELLER_PASSWORD = "ABcd5678#&";

    User TEST_SELLER = User.builder()
        .loginId(TEST_SELLER_LOGIN_ID)
        .nickname(TEST_SELLER_NICKNAME)
        .password(TEST_SELLER_PASSWORD)
        .role(UserRole.USER)
        .provider(Provider.LOCAL)
        .build();
}
