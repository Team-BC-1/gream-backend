package bc1.gream.domain.user.controller;

import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.dto.response.UserSignupResponseDto;
import bc1.gream.domain.user.service.impl.UserServiceImpl;
import bc1.gream.global.common.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/signup")
    public RestResponse<UserSignupResponseDto> signup(@Valid @RequestBody UserSignupRequestDto request) {
        UserSignupResponseDto response = userServiceImpl.signup(request);
        return RestResponse.success(response);
    }
}
