package bc1.gream.global.security;

import bc1.gream.test.UserTest;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String loginId() default UserTest.TEST_USER_LOGIN_ID;

    String password() default UserTest.TEST_USER_PASSWORD;
}
