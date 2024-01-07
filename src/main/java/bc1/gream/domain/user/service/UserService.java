package bc1.gream.domain.user.service;

import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.dto.response.UserSignupResponseDto;

public interface UserService {

    UserSignupResponseDto signup(UserSignupRequestDto request);
}
