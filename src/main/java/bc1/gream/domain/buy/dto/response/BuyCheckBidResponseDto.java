package bc1.gream.domain.buy.dto.response;

import lombok.Builder;

@Builder
public record BuyCheckBidResponseDto(
    Long buyId,
    Long productId,
    String productBrand,
    String productName,
    Long couponId,
    String couponName,
    Long price,
    Long discountPrice
) {

}
