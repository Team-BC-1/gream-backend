package bc1.gream.domain.coupon.controller;

import bc1.gream.domain.coupon.dto.response.CouponAvailableResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUnavailableResponseDto;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("")
    public RestResponse<List<CouponAvailableResponseDto>> couponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CouponAvailableResponseDto> response = couponService.couponList(userDetails);
        return RestResponse.success(response);
    }

    @PostMapping("/used")
    public RestResponse<List<CouponUnavailableResponseDto>> usedCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CouponUnavailableResponseDto> response = couponService.unavailableCouponList(userDetails);
        return RestResponse.success(response);
    }
}
