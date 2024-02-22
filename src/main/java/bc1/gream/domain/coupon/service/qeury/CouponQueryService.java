package bc1.gream.domain.coupon.service.qeury;

import static bc1.gream.global.common.ResultCase.COUPON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.security.UserDetailsImpl;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public Coupon checkCoupon(Long couponId, User buyer, CouponStatus status) {

        Coupon coupon = findCouponById(couponId, buyer);

        if (!isCheckCouponStatus(coupon, status)) {
            throw new GlobalException(ResultCase.COUPON_STATUS_CHANGE_FAIL);
        }

        return coupon;
    }

    private boolean isCheckCouponStatus(Coupon coupon, CouponStatus status) {
        return coupon.getStatus().equals(status);
    }

    public List<Coupon> availableCouponList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return couponRepository.availableCoupon(user);
    }

    public List<Coupon> unavailableCouponList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return couponRepository.unavailable(user);
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

    public boolean isMatchCouponUser(User user, Coupon coupon) {
        return coupon.getUser().getLoginId().equals(user.getLoginId());
    }


    /**
     * 구매입찰로부터 쿠폰 조회
     *
     * @param buy 구매입찰
     * @return 구매입찰 시 등록된 쿠폰, 없다면 null 반환
     */
    public Coupon getCouponFrom(Buy buy) {
        if (Objects.isNull(buy.getCouponId())) {
            return null;
        }
        // 쿠폰 조회, 사용처리
        Coupon coupon = findCouponById(buy.getCouponId(), buy.getUser());
        coupon.changeStatus(CouponStatus.ALREADY_USED);
        return coupon;
    }
}
