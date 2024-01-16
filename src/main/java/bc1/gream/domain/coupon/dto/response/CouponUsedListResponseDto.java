package bc1.gream.domain.coupon.dto.response;

import bc1.gream.domain.coupon.entity.DiscountType;
import lombok.Builder;

@Builder
public record CouponUsedListResponseDto(
    String name,
    DiscountType discountType,
    Long discount
) {

}
