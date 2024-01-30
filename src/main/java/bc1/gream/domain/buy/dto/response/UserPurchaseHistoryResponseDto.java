package bc1.gream.domain.buy.dto.response;

import java.time.LocalDateTime;

public record UserPurchaseHistoryResponseDto(
    Long orderId,
    LocalDateTime orderCreatedAt,
    Long orderExpectedPrice,
    Long orderFinalPrice,
    Long productId,
    String productBrand,
    String productName,
    Long gifticonId,
    String gifticonUrl
) {

}
