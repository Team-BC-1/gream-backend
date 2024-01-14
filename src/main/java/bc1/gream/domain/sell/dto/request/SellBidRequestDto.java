package bc1.gream.domain.sell.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SellBidRequestDto(
    @NotNull(message = "가격 필드는 비울 수 없습니다.")
    Long price,
    Integer period,
    @NotBlank(message = "기프티콘을 업로드해 주세요")
    String gifticonUrl
) {

}
