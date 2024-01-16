package bc1.gream.domain.sell.dto.response;

import java.time.LocalDateTime;

public record UserSoldGifticonResponseDto(
    Long orderId,
    LocalDateTime tradedDate,
    Long finalPrice,
    Long productId,
    String brand,
    String name,
    String iamgeUrl
) {

}
