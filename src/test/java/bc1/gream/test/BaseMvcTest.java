package bc1.gream.test;

import bc1.gream.global.security.UserDetailsImpl;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class BaseMvcTest implements UserTest {

    protected Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        mockUserSetup();
    }

    private void mockUserSetup() {
        UserDetails testUserDetails = new UserDetailsImpl(TEST_USER);
        mockPrincipal =
            new UsernamePasswordAuthenticationToken(
                testUserDetails, "", testUserDetails.getAuthorities());
    }
}
