package bc1.gream.domain.user.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record UserLoginResponseDto(
    Long userId,
    String loginId,
    String nickname,
    String role,
    String provider,
    List<Long> likeProducts
) {

}
