package bc1.gream.domain.payment.toss.dto.response;

import lombok.Builder;

@Builder
public record TossPaymentFailResponseDto(
    String errorCode,
    String errorMsg,
    String orderId
) {

}
