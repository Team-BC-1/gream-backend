package bc1.gream.domain.user.controller;

import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.dto.response.UserPointResponseDto;
import bc1.gream.domain.user.dto.response.UserSignupResponseDto;
import bc1.gream.domain.user.service.UserService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public RestResponse<UserSignupResponseDto> signup(@Valid @RequestBody UserSignupRequestDto request) {
        UserSignupResponseDto response = userService.signup(request);
        return RestResponse.success(response);
    }

    @PostMapping("/points")
    public RestResponse<UserPointResponseDto> points(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserPointResponseDto response = userService.points(userDetails);
        return RestResponse.success(response);
    }
}
