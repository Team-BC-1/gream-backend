package bc1.gream.domain.sell.dto.response;

import java.time.LocalDateTime;

public record UserSalesHistroyResponseDto(
    Long orderId,
    LocalDateTime orderTradedDate,
    Long orderFinalPrice,
    Long productId,
    String productBrand,
    String productName,
    String productIamgeUrl
) {

}
