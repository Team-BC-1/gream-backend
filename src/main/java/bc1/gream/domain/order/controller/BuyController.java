package bc1.gream.domain.order.controller;

import static bc1.gream.domain.user.entity.CouponStatus.*;

import bc1.gream.domain.order.dto.request.BuyBidRequestDto;
import bc1.gream.domain.order.dto.response.BuyBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.order.service.BuyCouponService;
import bc1.gream.domain.order.service.BuyService;
import bc1.gream.domain.user.entity.CouponStatus;
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
    private final BuyCouponService buyCouponService;

    @PostMapping("/{productId}")
    public RestResponse<BuyBidResponseDto> buyBidProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody BuyBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        BuyBidResponseDto responseDto = buyService.buyBidProduct(userDetails.getUser(), requestDto, productId);
        buyCouponService.changeCouponStatusByCouponId(requestDto.couponId(), INUSE);
        return RestResponse.success(responseDto);
    }

    @DeleteMapping("/{buyId}")
    public RestResponse<BuyCancelBidResponseDto> buyCancelBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long buyId
    ) {
        buyCouponService.changeCouponStatus(buyId, AVAILABLE);
        BuyCancelBidResponseDto responseDto = buyService.buyCancelBid(userDetails.getUser(), buyId);
        return RestResponse.success(responseDto);
    }
}
