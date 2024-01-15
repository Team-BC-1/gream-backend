package bc1.gream.domain.buy.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BuyBidRequestDto(
    @NotNull(message = "가격 필드는 비울 수 없습니다.")
    Long price,
    Integer period,
    Long couponId
) {

}
