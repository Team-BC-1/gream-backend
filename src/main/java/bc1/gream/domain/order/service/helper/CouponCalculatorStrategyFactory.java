package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.DiscountType;
import java.util.Optional;

public final class CouponCalculatorStrategyFactory {

    public static Optional<CouponCalculatorStrategy> getCouponCalculatorStrategy(DiscountType discountType) {
        if (discountType.equals(DiscountType.FIX)) {
            return Optional.of(new CouponFixedCalculation());
        }
        if (discountType.equals(DiscountType.RATE)) {
            return Optional.of(new CouponRateCalculation());
        }
        return Optional.empty();
    }
}
