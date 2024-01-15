package bc1.gream.domain.user.coupon.entity;

public enum DiscountType {
    RATE {
        @Override
        public Long calculateDiscount(Coupon coupon, Long price) {
            return price * (100 - coupon.getDiscount()) / 100;
        }
    },
    FIX {
        @Override
        public Long calculateDiscount(Coupon coupon, Long price) {
            return price - coupon.getDiscount();
        }
    };

    public abstract Long calculateDiscount(Coupon coupon, Long price);
}
