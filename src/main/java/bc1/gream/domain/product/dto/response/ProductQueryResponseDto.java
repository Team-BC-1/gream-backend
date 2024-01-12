package bc1.gream.domain.product.dto.response;

public record ProductQueryResponseDto(
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
