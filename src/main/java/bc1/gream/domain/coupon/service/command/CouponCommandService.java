package bc1.gream.domain.coupon.service.command;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponCommandService {

    private final CouponRepository couponRepository;

    public void changeCouponStatus(Coupon coupon, CouponStatus couponStatus) {
        coupon.changeStatus(couponStatus);
    }

    public Coupon createCoupon(User user, AdminCreateCouponRequestDto adminCreateCouponRequestDto) {
        Coupon coupon = Coupon.builder()
            .name(adminCreateCouponRequestDto.name())
            .discountType(adminCreateCouponRequestDto.discountType())
            .discount(adminCreateCouponRequestDto.discount())
            .status(CouponStatus.AVAILABLE)
            .user(user)
            .build();
        return couponRepository.save(coupon);
    }
}
