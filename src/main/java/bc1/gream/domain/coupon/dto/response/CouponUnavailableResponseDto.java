package bc1.gream.domain.coupon.dto.response;

import lombok.Builder;

@Builder
public record CouponUnavailableResponseDto(
    Long couponId,
    String couponName,
    String couponDiscountType,
    Long couponDiscount
) {

}
