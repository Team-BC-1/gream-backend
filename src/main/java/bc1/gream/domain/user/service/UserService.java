package bc1.gream.domain.user.service;

import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.dto.response.UserPointResponseDto;
import bc1.gream.domain.user.dto.response.UserSignupResponseDto;
import bc1.gream.global.security.UserDetailsImpl;

public interface UserService {

    UserSignupResponseDto signup(UserSignupRequestDto request);

    UserPointResponseDto points(UserDetailsImpl userDetails);
}
