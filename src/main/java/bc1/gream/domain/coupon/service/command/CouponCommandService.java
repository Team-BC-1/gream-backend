package bc1.gream.domain.coupon.service.command;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    public void changeCouponStatus(Coupon coupon, CouponStatus couponStatus) {
        coupon.changeStatus(couponStatus);
    }
}
