package bc1.gream.domain.product.dto.response;

import lombok.Builder;

@Builder
public record ProductPreviewResponseDto(
    Long productId,
    String productBrand,
    String productName,
    String productImageUrl,
    String productDescription,
    Long productPrice
) {

}
