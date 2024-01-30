package bc1.gream.domain.coupon.dto.response;

import lombok.Builder;

@Builder
public record CouponAvailableResponseDto(
    Long couponId,
    String couponName,
    String couponDiscountType,
    Long couponDiscount
) {

}
