package bc1.gream.domain.admin.dto;

public record AdminProductRequestDto(
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
