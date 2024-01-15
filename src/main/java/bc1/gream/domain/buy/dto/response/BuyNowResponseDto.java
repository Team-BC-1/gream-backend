package bc1.gream.domain.buy.dto.response;

public record BuyNowResponseDto(
    Long orderId,
    Long expectedPrice,
    Long finalPrice
) {

}
