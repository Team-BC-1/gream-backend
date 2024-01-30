package bc1.gream.domain.admin.dto.response;

import lombok.Builder;

@Builder // 테스트용
public record AdminGetRefundResponseDto(
    Long refundId,
    Long userId,
    Long refundPoint,
    String refundBank,
    String refundAccountNumber
) {

}
