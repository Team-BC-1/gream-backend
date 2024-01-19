package bc1.gream.domain.coupon.controller;

import bc1.gream.domain.coupon.dto.response.CouponAvailableResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUnavailableResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.mapper.CouponMapper;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
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

    private final CouponQueryService couponQueryService;

    @GetMapping("")
    @Operation(summary = "사용가능한 쿠폰 조회 요청", description = "사용자가 소유한 사용가능 쿠폰 조회요청을 처리합니다.")
    public RestResponse<List<CouponAvailableResponseDto>> availableCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Coupon> coupons = couponQueryService.availableCouponList(userDetails);
        List<CouponAvailableResponseDto> response = coupons.stream().map(CouponMapper.INSTANCE::toCouponListResponseDto).toList();
        return RestResponse.success(response);
    }

    @GetMapping("/used")
    @Operation(summary = "사용완료한 쿠폰 조회 요청", description = "사용자가 소유한 사용완료 쿠폰 조회요청을 처리합니다.")
    public RestResponse<List<CouponUnavailableResponseDto>> unavailableCouponList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Coupon> coupons = couponQueryService.unavailableCouponList(userDetails);
        List<CouponUnavailableResponseDto> response = coupons.stream().map(CouponMapper.INSTANCE::toCouponUsedListResponseDto).toList();
        return RestResponse.success(response);
    }
}
