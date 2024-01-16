package bc1.gream.domain.coupon.dto.response;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.DiscountType;
import java.util.List;
import lombok.Builder;

@Builder
public record CouponListResponseDto(
    String name,
    DiscountType discountType,
    Long discount
) {

}
