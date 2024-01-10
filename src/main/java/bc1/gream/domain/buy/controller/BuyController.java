package bc1.gream.domain.buy.controller;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
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
public class BuyController {

    private final BuyService buyService;

    @PostMapping("/{productId}")
    public RestResponse<BuyBidResponseDto> buyBidProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody BuyBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        BuyBidResponseDto responseDto = buyService.buyBidProduct(userDetails.getUser(), requestDto, productId);
        return RestResponse.success(responseDto);
    }
}