package bc1.gream.domain.sell.dto.response;

import java.time.LocalDateTime;

public record OrderAsSellerResponseDto(
    Long orderId,
    LocalDateTime tradedDate,
    Long finalPrice,
    Long productId,
    String productBrand,
    String productName,
    String iamgeUrl
) {

}
