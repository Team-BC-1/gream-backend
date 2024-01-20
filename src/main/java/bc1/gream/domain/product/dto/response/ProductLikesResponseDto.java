package bc1.gream.domain.product.dto.response;

public record ProductLikesResponseDto(
    Long productId,
    String productBrand,
    String productName,
    String productImageUrl,
    String productDescription,
    Long productPrice
) {

}
