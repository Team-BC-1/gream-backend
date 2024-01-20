package bc1.gream.domain.product.dto.response;

import lombok.Builder;

@Builder
public record ProductPreviewByNameResponseDto(
    Long productId,
    String productBrand,
    String productName,
    String productImageUrl,
    String productDescription,
    Long productPrice
) {

}
