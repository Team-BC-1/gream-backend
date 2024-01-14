package bc1.gream.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserLoginResponseDto(
    Long userId,
    String loginId,
    String nickname,
    String role,
    String provider
) {

}
