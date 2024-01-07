package bc1.gream.domain.user.dto.request;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@JsonSerialize
@Builder // 테스트 용
public record UserSignupRequestDto(String loginId, String nickname, String password) {

}
