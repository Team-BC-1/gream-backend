package bc1.gream.domain.payment.toss.dto.response;

public record TossPaymentFailResponseDto(
    String errorCode,
    String errorMsg,
    Long orderId
) {

}
