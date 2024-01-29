package bc1.gream.domain.user.dto.request;

public record UserPointRefundRequestDto(
    Long point,
    String bank,
    String accountNumber
) {

}
