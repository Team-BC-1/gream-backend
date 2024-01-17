package bc1.gream.domain.product.dto.response;

public record ProductLikesResponseDto(
    Long id,
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
