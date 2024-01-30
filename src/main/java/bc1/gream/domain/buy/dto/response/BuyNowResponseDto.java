package bc1.gream.domain.buy.dto.response;

public record BuyNowResponseDto(
    Long orderId,
    Long orderExpectedPrice,
    Long orderFinalPrice
) {

}
