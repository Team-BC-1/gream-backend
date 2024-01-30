package bc1.gream.domain.sell.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserSellBidOnProgressResponseDto(
    Long sellId,
    Long productId,
    String productBrand,
    String productName,
    Long gifticonId,
    String gifticonImageUrl,
    Long sellPrice,
    LocalDateTime sellBidStartedAt,
    LocalDateTime sellBidDeadlineAt
) {

}
