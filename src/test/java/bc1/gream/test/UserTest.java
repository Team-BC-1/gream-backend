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
}
