package bc1.gream.domain.user.service;

import static bc1.gream.global.common.ResultCase.COUPON_NOT_FOUND;

import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.CouponStatus;
import bc1.gream.domain.user.repository.CouponRepository;
import bc1.gream.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void changeCouponStatus(Long couponId, CouponStatus couponStatus) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
            () -> new GlobalException(COUPON_NOT_FOUND)
        );

        coupon.changeStatus(couponStatus);
    }
}
