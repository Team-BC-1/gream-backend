package bc1.gream.domain.admin.controller;


import bc1.gream.domain.admin.dto.request.AdminGetRefundRequestDto;
import bc1.gream.domain.admin.dto.request.AdminProductRequestDto;
import bc1.gream.domain.admin.dto.response.AdminGetRefundResponseDto;
import bc1.gream.domain.admin.dto.response.AdminProductResponseDto;
import bc1.gream.domain.admin.mapper.RefundMapper;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.user.service.query.RefundQueryService;
import bc1.gream.global.common.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {


    private final ProductService productService;
    private final RefundQueryService refundQueryService;

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
        productService.addProduct(adminProductRequestDto);
        return RestResponse.success(new AdminProductResponseDto());
    }

}
