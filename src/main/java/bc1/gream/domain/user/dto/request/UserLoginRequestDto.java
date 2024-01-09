package bc1.gream.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder // 테스트용
public record UserLoginRequestDto(
    @Pattern(
        regexp = "^[a-zA-Z0-9]{4,20}$",
        message = "로그인 아이디는 영문자 및 숫자, 4이상 20이하 길이로 가능합니다.")
    String loginId,

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9@#$%^&+=]{8,30}$",
        message = "비밀번호는 영소문자, 숫자가 필수이고, 8이상 30이하 길이로 가능합니다.")
    String password
) {

}
