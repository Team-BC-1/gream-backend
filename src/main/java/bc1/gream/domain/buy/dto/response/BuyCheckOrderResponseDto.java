package bc1.gream.domain.buy.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BuyCheckOrderResponseDto(
    Long orderId,
    LocalDateTime orderCreatedAt,
    Long expectedPrice,
    Long finalPrice,
    Long productId,
    String productBrand,
    String productName,
    Long gifticonId,
    String gifticonUrl
) {

}
