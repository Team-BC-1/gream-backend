package bc1.gream.domain.coupon.provider;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponProvider {

    private final UserRepository userRepository;
    private final CouponCommandService couponCommandService;

    @Transactional
    public Coupon createCoupon(AdminCreateCouponRequestDto adminCreateCouponRequestDto) {
        User user = userRepository.findByLoginId(adminCreateCouponRequestDto.userLoginId())
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
        return couponCommandService.createCoupon(user, adminCreateCouponRequestDto);
    }
}
