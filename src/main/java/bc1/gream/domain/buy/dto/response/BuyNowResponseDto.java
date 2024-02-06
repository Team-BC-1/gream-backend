package bc1.gream.domain.buy.dto.response;

import lombok.Builder;

@Builder    //테스트 용
public record BuyNowResponseDto(
    Long orderId,
    Long expectedPrice,
    Long finalPrice
) {

}
