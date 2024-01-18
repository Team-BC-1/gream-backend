package bc1.gream.domain.sell.controller;

import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.provider.SellNowProvider;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    private final SellNowProvider sellNowProvider;

    /**
     * 즉시판매 체결 요청
     *
     * @param userDetails 판매자
     * @param productId   상품 아이디
     * @param requestDto  가격과 기프티콘 이미지
     * @return
     */
    @PostMapping("/{productId}/now")
    public RestResponse<SellNowResponseDto> sellNowProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long productId,
        @Valid @RequestBody SellNowRequestDto requestDto
    ) {
        SellNowResponseDto responseDto = sellNowProvider.sellNowProduct(userDetails.getUser(), requestDto, productId);
        return RestResponse.success(responseDto);
    }
}
