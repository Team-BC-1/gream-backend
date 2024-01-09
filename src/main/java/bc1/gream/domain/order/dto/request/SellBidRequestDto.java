package bc1.gream.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SellBidRequestDto(
    @NotBlank(message = "가격 필드는 비울 수 없습니다.")
    Long price,
    Integer period,
    @NotBlank(message = "판매 할 기프티콘의 경로를 알려주세요")
    String gifticonUrl
) {

}
