package bc1.gream.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties
public record UserPointResponseDto(
    Long point
) {

}
