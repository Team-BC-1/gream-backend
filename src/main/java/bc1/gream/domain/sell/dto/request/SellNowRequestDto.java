package bc1.gream.domain.sell.dto.request;

import lombok.Builder;

@Builder
public record SellNowRequestDto(
    Long price,
    String paymentType,
    String gifticonUrl
) {

}
