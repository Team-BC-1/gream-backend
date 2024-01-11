package bc1.gream.domain.order.controller;

import static bc1.gream.domain.user.entity.CouponStatus.ALREADY_USED;
import static bc1.gream.domain.user.entity.CouponStatus.AVAILABLE;
import static bc1.gream.domain.user.entity.CouponStatus.IN_USE;

import bc1.gream.domain.common.facade.ChangingCouponStatusFacade;
import bc1.gream.domain.order.dto.request.BuyBidRequestDto;
import bc1.gream.domain.order.dto.request.BuyNowRequestDto;
import bc1.gream.domain.order.dto.response.BuyBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyNowResponseDto;
import bc1.gream.domain.order.service.BuyService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final ChangingCouponStatusFacade changingCouponStatusFacade;

    @PostMapping("/{productId}")
    public RestResponse<BuyBidResponseDto> buyBidProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody BuyBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        BuyBidResponseDto responseDto = buyService.buyBidProduct(userDetails.getUser(), requestDto, productId);
        changingCouponStatusFacade.changeCouponStatusByCouponId(requestDto.couponId(), userDetails.getUser(), IN_USE);
        return RestResponse.success(responseDto);
    }

    @DeleteMapping("/bid/{buyId}")
    public RestResponse<BuyCancelBidResponseDto> buyCancelBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long buyId
    ) {
        changingCouponStatusFacade.changeCouponStatus(buyId, userDetails.getUser(), AVAILABLE);
        BuyCancelBidResponseDto responseDto = buyService.buyCancelBid(userDetails.getUser(), buyId);
        return RestResponse.success(responseDto);
    }

    @PostMapping("/{productId}/now")
    public RestResponse<BuyNowResponseDto> buyNowProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long productId,
        @RequestBody BuyNowRequestDto requestDto
    ) {
        changingCouponStatusFacade.changeCouponStatusByCouponId(requestDto.couponId(), userDetails.getUser(), ALREADY_USED);
        BuyNowResponseDto responseDto = buyService.buyNowProduct(userDetails.getUser(), requestDto, productId);

        return RestResponse.success(responseDto);
    }
}
