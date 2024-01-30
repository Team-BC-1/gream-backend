package bc1.gream.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserPointRefundResponseDto(
    Long refundPoint,
    Long userPoint
) {

}
