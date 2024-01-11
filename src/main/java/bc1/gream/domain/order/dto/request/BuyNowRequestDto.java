package bc1.gream.domain.order.dto.request;

import lombok.Builder;

@Builder
public record BuyNowRequestDto(
    Long price,
    Long couponId
) {

}
