package bc1.gream.domain.coupon.dto.response;

import lombok.Builder;

@Builder
public record CouponUnavailableResponseDto(
    String name,
    String discountType,
    Long discount
) {

}
