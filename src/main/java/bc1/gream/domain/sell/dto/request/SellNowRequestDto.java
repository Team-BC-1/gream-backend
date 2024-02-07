package bc1.gream.domain.sell.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record SellNowRequestDto(
    @NotNull(message = "가격 필드는 비울 수 없습니다.")
    Long price,
    @NotNull(message = "기프티콘을 업로드해 주세요")
    MultipartFile file
) {

}
