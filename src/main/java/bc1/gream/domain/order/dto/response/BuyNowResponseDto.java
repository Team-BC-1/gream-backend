package bc1.gream.domain.order.dto.response;

public record BuyNowResponseDto(
    Long orderId,
    Long expectedPrice,
    Long finalPrice
) {

}
