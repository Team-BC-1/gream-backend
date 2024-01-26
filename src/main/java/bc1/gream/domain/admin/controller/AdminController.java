package bc1.gream.domain.admin.controller;

import bc1.gream.domain.admin.dto.request.AdminGetRefundRequestDto;
import bc1.gream.domain.admin.dto.response.AdminGetRefundResponseDto;
import bc1.gream.domain.admin.mapper.RefundMapper;
import bc1.gream.domain.user.service.UserService;
import bc1.gream.global.common.RestResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/refunds")
    public RestResponse<List<AdminGetRefundResponseDto>> getRefunds(AdminGetRefundRequestDto requestDto) {

        List<AdminGetRefundResponseDto> response = userService.getRefunds().stream()
            .map(RefundMapper.INSTANCE::toAdminGetRefundResponseDto)
            .toList();

        return RestResponse.success(response);
    }
}
