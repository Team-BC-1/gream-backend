package bc1.gream.domain.buy.dto.response;

import lombok.Builder;

@Builder    // 테스트용
public record BuyBidResponseDto(
    Long price,
    Long buyId
) {

}
