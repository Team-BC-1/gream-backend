package bc1.gream.domain.coupon.service;

import static bc1.gream.global.common.ResultCase.COUPON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.coupon.dto.response.CouponListResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUsedListResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.mapper.CouponMapper;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.product.dto.response.ProductPreviewResponseDto;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.mapper.UserMapper;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.security.UserDetailsImpl;
import java.util.List;
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

    public boolean isMatchCouponUser(User user, Coupon coupon) {
        return coupon.getUser().getLoginId().equals(user.getLoginId());
    }

    public List<CouponListResponseDto> couponList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Coupon> coupons = couponRepository.availableCoupon(user);
        return coupons.stream().map(CouponMapper.INSTANCE::toCouponListResponseDto).toList();
    }

    public List<CouponUsedListResponseDto> unavailableCouponList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Coupon> coupons = couponRepository.unavailable(user);
        return coupons.stream().map(CouponMapper.INSTANCE::toCouponUsedListResponseDto).toList();

    }

}
