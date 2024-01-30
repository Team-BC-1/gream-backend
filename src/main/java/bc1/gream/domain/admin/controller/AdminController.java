package bc1.gream.domain.admin.controller;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.admin.dto.response.AdminCreateCouponResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.mapper.CouponMapper;
import bc1.gream.domain.coupon.provider.CouponProvider;
import bc1.gream.global.common.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final CouponProvider couponProvider;

    @PostMapping("/coupon")
    @Operation(summary = "쿠폰 생성요청 [어드민 ONLY]", description = "어드민 권한의 관리자의 쿠폰 생성 요청을 처리합니다.")
    public RestResponse<AdminCreateCouponResponseDto> createCoupon(
        @Valid @RequestBody AdminCreateCouponRequestDto adminCreateCouponRequestDto
    ) {
        Coupon coupon = couponProvider.createCoupon(adminCreateCouponRequestDto);
        AdminCreateCouponResponseDto responseDto = CouponMapper.INSTANCE.toAdminCreateCouponResponseDto(coupon);
        return RestResponse.success(responseDto);
    }
}