package bc1.gream.domain.sell.controller;

import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.provider.SellNowProvider;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
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
    @Operation(summary = "즉시판매 체결 요청", description = "상품에 대한 판매자의 즉시판매 체결요청을 처리합니다.")
    public RestResponse<SellNowResponseDto> sellNowProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long productId,
        @Valid SellNowRequestDto requestDto
    ) {
        SellNowResponseDto responseDto = sellNowProvider.sellNowProduct(userDetails.getUser(), requestDto, productId);
        return RestResponse.success(responseDto);
    }
}
