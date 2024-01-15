package bc1.gream.domain.buy.dto.response;

import lombok.Builder;

@Builder
public record BuyCancelBidResponseDto(
    Long buyId
) {

}
