package bc1.gream.domain.admin.controller;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.admin.dto.request.AdminGetRefundRequestDto;
import bc1.gream.domain.admin.dto.request.AdminProductRequestDto;
import bc1.gream.domain.admin.dto.request.AdminRefundPassResponseDto;
import bc1.gream.domain.admin.dto.response.AdminCreateCouponResponseDto;
import bc1.gream.domain.admin.dto.response.AdminGetRefundResponseDto;
import bc1.gream.domain.admin.dto.response.AdminProductResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.mapper.CouponMapper;
import bc1.gream.domain.coupon.provider.CouponProvider;
import bc1.gream.domain.product.service.command.ProductCommandService;
import bc1.gream.domain.user.mapper.RefundMapper;
import bc1.gream.domain.user.service.command.RefundCommandService;
import bc1.gream.domain.user.service.query.RefundQueryService;
import bc1.gream.global.common.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductCommandService productCommandService;
    private final RefundQueryService refundQueryService;
    private final RefundCommandService refundCommandService;
    private final CouponProvider couponProvider;

    @GetMapping("/refunds")
    @Operation(summary = "신청된 환급 리스트 조회 요청", description = "사용자가 신청한 환급 요청 리스트를 반환합니다.")
    public RestResponse<List<AdminGetRefundResponseDto>> getRefunds(AdminGetRefundRequestDto requestDto) {

        List<AdminGetRefundResponseDto> response = refundQueryService.getRefunds()
            .stream()
            .map(RefundMapper.INSTANCE::toAdminGetRefundResponseDto)
            .toList();

        return RestResponse.success(response);
    }

    @PostMapping("/products")
    public RestResponse<AdminProductResponseDto> addProducts(
        @RequestBody AdminProductRequestDto adminProductRequestDto
    ) {
        productCommandService.addProduct(adminProductRequestDto);

        return RestResponse.success(new AdminProductResponseDto());
    }

    @DeleteMapping("/refund/{id}")
    @Operation(summary = "유저 환급 승인", description = "유저가 신청한 환급 요청을 승인해주는 기능입니다.")
    public RestResponse<AdminRefundPassResponseDto> approveRefund(
        @PathVariable Long id
    ) {
        AdminRefundPassResponseDto responseDto = refundCommandService.approveRefund(id);

        return RestResponse.success(responseDto);
    }

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