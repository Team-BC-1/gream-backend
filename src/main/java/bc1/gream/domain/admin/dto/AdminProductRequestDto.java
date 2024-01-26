package bc1.gream.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record AdminProductRequestDto(
    String brand,
    String name,
    String imageUrl,
    String description,
    Long price
) {

}
