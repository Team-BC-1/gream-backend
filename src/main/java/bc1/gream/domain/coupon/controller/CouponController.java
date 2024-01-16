package bc1.gream.domain.coupon.controller;

import bc1.gream.domain.coupon.dto.response.CouponListResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUsedListResponseDto;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.domain.user.dto.response.UserPointResponseDto;
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
@RequestMapping("api/coupons")
public class CouponController {
    private CouponService couponService;

    @PostMapping("/")
    public RestResponse<List<CouponListResponseDto>> couponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CouponListResponseDto> response = couponService.couponList(userDetails);
        return RestResponse.success(response);
    }

    @PostMapping("/used")
    public RestResponse<List<CouponUsedListResponseDto>> usedCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CouponUsedListResponseDto> response = couponService.unavailableCouponList(userDetails);
        return RestResponse.success(response);
    }
}
