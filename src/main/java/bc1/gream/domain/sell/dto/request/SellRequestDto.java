package bc1.gream.domain.sell.dto.request;

import lombok.Builder;

@Builder
public record SellRequestDto(
    Long price,
    String paymentType,
    String gifticonUrl
) {

}
