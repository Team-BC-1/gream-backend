package bc1.gream.domain.order.controller;

import bc1.gream.domain.order.dto.request.SellNowRequestDto;
import bc1.gream.domain.order.dto.response.SellNowResponseDto;
import bc1.gream.domain.order.service.SellService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SellNowController {

    private final SellService sellService;

    @PostMapping("/{productId}/now")
    public RestResponse<SellNowResponseDto> sellNowProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long productId,
        @RequestBody SellNowRequestDto requestDto
    ) {
        SellNowResponseDto responseDto = sellService.sellNowProduct(userDetails.getUser(), requestDto, productId);
        return RestResponse.success(responseDto);
    }
}
