package bc1.gream.domain.coupon.service;

import static bc1.gream.global.common.ResultCase.COUPON_ALREADY_USED;
import static bc1.gream.global.common.ResultCase.COUPON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.COUPON_STATUS_CHANGE_FAIL;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void changeCouponStatus(Long couponId, User user, CouponStatus couponStatus) {
        Coupon coupon = findCouponById(couponId, user);
        checkCouponStatus(coupon, couponStatus);
        coupon.changeStatus(couponStatus);
    }

    public Coupon findCouponById(Long couponId, User user) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
            () -> new GlobalException(COUPON_NOT_FOUND)
        );

        if (!isMatchCouponUser(user, coupon)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        return coupon;
    }

    private boolean isMatchCouponUser(User user, Coupon coupon) {
        return coupon.getUser().getLoginId().equals(user.getLoginId());
    }

    private void checkCouponStatus(Coupon coupon, CouponStatus couponStatus) {
        if (coupon.getStatus().equals(couponStatus)) {
            throw new GlobalException(COUPON_STATUS_CHANGE_FAIL);
        }

        if (coupon.getStatus().equals(CouponStatus.ALREADY_USED)) {
            throw new GlobalException(COUPON_ALREADY_USED);
        }
    }
}
