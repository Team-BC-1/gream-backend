package bc1.gream.domain.buy.dto.response;

import bc1.gream.domain.coupon.entity.Coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserBuyBidOnProgressResponseDto(
    Long buyId,
    Long productId,
    String productBrand,
    String productName,
    Long couponId,
    String couponName,
    Long buyPrice,
    Long discountPrice,
    @JsonIgnore
    Coupon coupon,
    LocalDateTime buyBidStartedAt,
    LocalDateTime buyBidDeadlineAt
) {

}
