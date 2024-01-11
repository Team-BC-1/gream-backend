package bc1.gream.domain.common.facade;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCouponFacade {

    private final CouponService couponService;

    public Coupon findByCouponId(Long couponId, User user) {
        return couponService.findCouponById(couponId, user);
    }
}
