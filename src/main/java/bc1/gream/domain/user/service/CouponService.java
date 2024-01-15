package bc1.gream.domain.user.service;

import static bc1.gream.global.common.ResultCase.COUPON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.user.coupon.entity.Coupon;
import bc1.gream.domain.user.coupon.entity.CouponStatus;
import bc1.gream.domain.user.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void changeCouponStatus(Long couponId, User user, CouponStatus couponStatus) {
        Coupon coupon = findCouponById(couponId, user);

        coupon.changeStatus(couponStatus);
    }

    @Transactional(readOnly = true)
    public Coupon findCouponById(Long couponId, User user) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
            () -> new GlobalException(COUPON_NOT_FOUND)
        );

        if (!isMatchCouponUser(user, coupon)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        return coupon;
    }

    boolean isMatchCouponUser(User user, Coupon coupon) {
        return coupon.getUser().getLoginId().equals(user.getLoginId());
    }
}
