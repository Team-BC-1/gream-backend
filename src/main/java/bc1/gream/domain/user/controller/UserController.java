package bc1.gream.domain.user.controller;

import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.dto.response.UserPointResponseDto;
import bc1.gream.domain.user.dto.response.UserSignupResponseDto;
import bc1.gream.domain.user.service.UserService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    @SecurityRequirements() // Swagger Security 적용 X
    @Operation(summary = "사용자 회원가입", description = "사용자의 회원가입요청을 처리합니다.")
    public RestResponse<UserSignupResponseDto> signup(@Valid @RequestBody UserSignupRequestDto request) {
        UserSignupResponseDto response = userService.signup(request);
        return RestResponse.success(response);
    }

    @GetMapping("/points")
    @Operation(summary = "사용자 포인트 조회 요청", description = "사용자의 포인트에 대한 조회요청을 처리합니다.")
    public RestResponse<UserPointResponseDto> pointsCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserPointResponseDto response = userService.pointsCheck(userDetails);
        return RestResponse.success(response);
    }
}
