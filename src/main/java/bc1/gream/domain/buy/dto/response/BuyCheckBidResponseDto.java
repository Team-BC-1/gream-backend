package bc1.gream.domain.buy.dto.response;

import bc1.gream.domain.coupon.entity.Coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public record BuyCheckBidResponseDto(
    Long buyId,
    Long productId,
    String productBrand,
    String productName,
    Long couponId,
    String couponName,
    Long price,
    Long discountPrice,
    @JsonIgnore
    Coupon coupon
) {

}
