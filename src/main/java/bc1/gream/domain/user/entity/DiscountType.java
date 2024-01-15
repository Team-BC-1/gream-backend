package bc1.gream.domain.user.entity;

import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;

public enum DiscountType {
    RATE {
        @Override
        public Long calculateDiscount(Coupon coupon, Long price) {
            if (coupon.getDiscount() < 0 || coupon.getDiscount() > 100) {
                throw new GlobalException(ResultCase.COUPON_TYPE_INVALID_RATE);
            }
            return price * (100 - coupon.getDiscount()) / 100;
        }
    },
    FIX {
        @Override
        public Long calculateDiscount(Coupon coupon, Long price) {
            if (coupon.getDiscount() > price) {
                throw new GlobalException(ResultCase.COUPON_TYPE_INVALID_FIXED);
            }
            return price - coupon.getDiscount();
        }
    };

    public abstract Long calculateDiscount(Coupon coupon, Long price);
}
