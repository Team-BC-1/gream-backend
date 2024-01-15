package bc1.gream.domain.buy.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record BuyCancelBidResponseDto(
    Long buyId
) {

}
