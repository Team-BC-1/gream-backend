package bc1.gream.domain.coupon.service.command;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;

    public void changeCouponStatus(Coupon coupon, CouponStatus couponStatus) {
        coupon.changeStatus(couponStatus);
    }

    public Coupon createCoupon(AdminCreateCouponRequestDto adminCreateCouponRequestDto) {
        Coupon coupon = Coupon.builder()
            .name(adminCreateCouponRequestDto.name())
            .discountType(adminCreateCouponRequestDto.discountType())
            .discount(adminCreateCouponRequestDto.discount())
            .status(adminCreateCouponRequestDto.status())
            .build();
        return couponRepository.save(coupon);
    }
}
