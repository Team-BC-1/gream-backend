package bc1.gream.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BuyNowRequestDto(
    @NotNull(message = "가격 필드는 비울 수 없습니다.")
    Long price,
    Long couponId
) {

}
