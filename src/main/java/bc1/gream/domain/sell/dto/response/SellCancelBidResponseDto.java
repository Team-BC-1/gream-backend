package bc1.gream.domain.sell.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record SellCancelBidResponseDto(
    Long id
) {

}
