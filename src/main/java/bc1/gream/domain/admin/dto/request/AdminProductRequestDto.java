package bc1.gream.domain.admin.dto.request;

import lombok.Builder;

@Builder
public record AdminProductRequestDto(
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
