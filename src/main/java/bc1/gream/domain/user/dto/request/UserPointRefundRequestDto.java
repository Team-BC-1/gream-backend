package bc1.gream.domain.user.dto.request;

import lombok.Builder;

@Builder    // 테스트용
public record UserPointRefundRequestDto(
    Long point,
    String bank,
    String accountNumber
) {

}
