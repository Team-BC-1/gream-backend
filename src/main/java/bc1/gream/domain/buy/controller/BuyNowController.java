package bc1.gream.domain.buy.controller;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.provider.BuyNowProvider;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buy")
@RequiredArgsConstructor
public class BuyNowController {

    private final BuyNowProvider buyNowProvider;

    /**
     * 즉시구매
     */
    @PostMapping("/{productId}/now")
    @Operation(summary = "즉시구매 요청", description = "상품에 대한 구매자의 즉시구매요청을 처리합니다.")
    public RestResponse<BuyNowResponseDto> buyNowProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long productId,
        @RequestBody BuyNowRequestDto requestDto
    ) {
        BuyNowResponseDto responseDto = buyNowProvider.buyNowProduct(userDetails.getUser(), requestDto, productId);
        return RestResponse.success(responseDto);
    }
}
