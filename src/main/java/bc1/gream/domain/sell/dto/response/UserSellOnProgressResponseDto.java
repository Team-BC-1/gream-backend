package bc1.gream.domain.sell.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserSellOnProgressResponseDto(
    Long sellId,
    Long sellPrice,
    LocalDateTime bidStartedAt,
    LocalDateTime bidDeadlineAt,
    Long productId,
    String productBrand,
    String productName,
    Long gifticonId,
    String gifticonImageUrl
) {

}
