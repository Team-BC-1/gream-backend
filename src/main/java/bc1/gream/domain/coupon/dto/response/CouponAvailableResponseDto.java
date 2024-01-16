package bc1.gream.domain.coupon.dto.response;

import lombok.Builder;

@Builder
public record CouponAvailableResponseDto(
    String name,
    String discountType,
    Long discount
) {

}
