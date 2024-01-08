package bc1.gream.domain.buy.dto.request;

import lombok.Builder;

@Builder
public record BuyBidRequestDto(
    Long price,
    Integer period
) {

}
