package bc1.gream.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record AdminProductRequestDto(
    @NotBlank(message = "상품 브랜드명을 입력해주세요")
    String brand,
    @NotBlank(message = "상품명을 입력해주세요.")
    String name,
    @NotNull(message = "상품 이미지를 입력해주세요.")
    MultipartFile file,
    @NotBlank(message = "상품 상세 설명을 입력해주세요.")
    String description,
    @NotNull(message = "가격 필드를 입력해주세요.")
    @Positive(message = "가격은 양수 입력만 가능합니다.")
    Long price
) {

}
