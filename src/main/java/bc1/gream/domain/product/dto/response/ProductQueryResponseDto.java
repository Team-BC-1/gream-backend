package bc1.gream.domain.product.dto.response;

public record ProductQueryResponseDto(
    Long id,
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
