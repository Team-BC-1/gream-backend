package bc1.gream.domain.admin.controller;

import bc1.gream.domain.admin.dto.request.AdminRefundPassResponseDto;
import bc1.gream.domain.admin.service.AdminService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public RestResponse<AdminRefundPassResponseDto> refundPass(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long id
    ) {
        AdminRefundPassResponseDto responseDto = adminService.refundPass(userDetails.getUser(), id);

        return RestResponse.success(responseDto);
    }
}
