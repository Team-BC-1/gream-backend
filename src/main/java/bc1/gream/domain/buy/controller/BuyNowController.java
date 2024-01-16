package bc1.gream.domain.buy.controller;

import static bc1.gream.domain.user.coupon.entity.CouponStatus.ALREADY_USED;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.service.BuyNowService;
import bc1.gream.domain.common.facade.ChangingCouponStatusFacade;
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
@RequestMapping("/api/buy")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BuyNowController {

    private final BuyNowService buyNowService;
    private final ChangingCouponStatusFacade changingCouponStatusFacade;

    /**
     * 즉시구매
     */
    @PostMapping("/{productId}/now")
    public RestResponse<BuyNowResponseDto> buyNowProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long productId,
        @RequestBody BuyNowRequestDto requestDto
    ) {
        changingCouponStatusFacade.changeCouponStatusByCouponId(requestDto.couponId(), userDetails.getUser(), ALREADY_USED);
        BuyNowResponseDto responseDto = buyNowService.buyNowProduct(userDetails.getUser(), requestDto, productId);
        return RestResponse.success(responseDto);
    }
}
