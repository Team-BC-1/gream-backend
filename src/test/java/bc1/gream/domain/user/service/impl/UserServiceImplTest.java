package bc1.gream.domain.user.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.test.UserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest implements UserTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Captor
    ArgumentCaptor<User> argumentCaptor;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup() {
        // given
        UserSignupRequestDto request = UserSignupRequestDto.builder().loginId(TEST_USER_LOGIN_ID).nickname(TEST_USER_NICKNAME)
            .password(passwordEncoder.encode(TEST_USER_PASSWORD)).build();

        given(userRepository.existsByLoginId(anyString())).willReturn(false);
        given(userRepository.existsByNickname(anyString())).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(TEST_USER);

        // when
        userService.signup(request);

        // then
        verify(passwordEncoder).encode(TEST_USER_PASSWORD);
        verify(userRepository).existsByNickname(TEST_USER_NICKNAME);
        verify(userRepository).existsByLoginId(TEST_USER_LOGIN_ID);
        verify(userRepository).save(any(User.class));

        verify(userRepository).save(argumentCaptor.capture());
        Assertions.assertThat(argumentCaptor.getValue().getNickname()).isEqualTo(TEST_USER_NICKNAME);
    }
}