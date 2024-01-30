package bc1.gream.domain.admin.controller;

import bc1.gream.domain.admin.dto.request.AdminRefundPassResponseDto;
import bc1.gream.domain.admin.service.AdminService;
import bc1.gream.global.common.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/refund/{id}")
    @Operation(summary = "유저 환급 승인", description = "유저가 신청한 환급 요청을 승인해주는 기능입니다.")
    public RestResponse<AdminRefundPassResponseDto> approveRefund(
        @PathVariable Long id
    ) {
        AdminRefundPassResponseDto responseDto = adminService.approveRefund(id);

        return RestResponse.success(responseDto);
    }
}
