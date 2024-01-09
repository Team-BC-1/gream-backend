package bc1.gream.domain.product.dto;

public record ProductQueryResponseDto(
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
