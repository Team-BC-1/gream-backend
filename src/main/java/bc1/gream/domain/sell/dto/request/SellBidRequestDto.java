package bc1.gream.domain.sell.dto.request;

import lombok.Builder;

@Builder // 테스트용
public record SellBidRequestDto(
    Long price,
    String paymentType,
    String gifticonUrl,
    Integer period
) {

}
