package bc1.gream.domain.coupon.controller;

import bc1.gream.domain.coupon.dto.response.CouponAvailableResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUnavailableResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.mapper.CouponMapper;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("")
    public RestResponse<List<CouponAvailableResponseDto>> availableCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Coupon> coupons = couponService.availableCouponList(userDetails);
        List<CouponAvailableResponseDto> response = coupons.stream().map(CouponMapper.INSTANCE::toCouponListResponseDto).toList();
        return RestResponse.success(response);
    }

    @GetMapping("/used")
    public RestResponse<List<CouponUnavailableResponseDto>> unavailableCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Coupon> coupons = couponService.unavailableCouponList(userDetails);
        List<CouponUnavailableResponseDto> response = coupons.stream().map(CouponMapper.INSTANCE::toCouponUsedListResponseDto).toList();
        return RestResponse.success(response);
    }
}
