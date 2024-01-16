package bc1.gream.domain.coupon.repository;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import java.util.List;

public interface CouponRepositoryCustom {
    List<Coupon> availableCoupon(User user);
    List<Coupon> unavailable(User user);
}
