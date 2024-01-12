package bc1.gream.domain.order.service.helper;

import bc1.gream.domain.user.entity.DiscountType;
import java.util.Optional;

public final class CouponCalculatorStrategyFactory {

    /**
     * {@link DiscountType}에 따라 {@link CouponCalculatorStrategy} 구현체 선택
     *
     * @param discountType 할인 종류
     * @return {@link CouponCalculatorStrategy} 구현체
     */
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
