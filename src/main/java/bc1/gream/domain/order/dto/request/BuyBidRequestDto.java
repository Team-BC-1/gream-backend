package bc1.gream.domain.order.dto.request;

import lombok.Builder;

@Builder
public record BuyBidRequestDto(
    Long price,
    Integer period
) {

}
