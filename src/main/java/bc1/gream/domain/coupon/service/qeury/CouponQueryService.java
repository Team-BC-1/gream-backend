package bc1.gream.domain.coupon.service.qeury;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public Coupon checkCoupon(Long couponId, User buyer, CouponStatus status) {

        Coupon coupon = findByCouponId(couponId);

        if (!isCheckCouponUser(coupon, buyer)) {
            throw new GlobalException(ResultCase.NOT_AUTHORIZED);
        }

        if (!isCheckCouponStatus(coupon, status)) {
            throw new GlobalException(ResultCase.COUPON_STATUS_CHANGE_FAIL);
        }

        return coupon;
    }

    private Coupon findByCouponId(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(
            () -> new GlobalException(ResultCase.COUPON_NOT_FOUND)
        );
    }

    private boolean isCheckCouponUser(Coupon coupon, User user) {
        return coupon.getUser().getLoginId().equals(user.getLoginId());
    }

    private boolean isCheckCouponStatus(Coupon coupon, CouponStatus status) {
        return coupon.getStatus().equals(status);
    }

}
