package bc1.gream.domain.admin.dto.request;

import bc1.gream.domain.coupon.entity.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@JsonIgnoreProperties
@Builder
public record AdminCreateCouponRequestDto(
    @NotBlank(message = "null 혹은 공백 입력은 불가합니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9가-힣]{4,30}$",
        message = "쿠폰이름은 영문자, 한글 및 숫자, 4이상 30이하 길이로 가능합니다."
    )
    String name,
    @NotNull(message = "null 입력은 불가합니다.")
    DiscountType discountType,
    @NotNull(message = "null 입력은 불가합니다.")
    @Positive(message = "음수 혹은 0 입력은 불가합니다.")
    Long discount,
    @NotBlank(message = "null 입력은 불가합니다.")
    String userLoginId
) {

}
