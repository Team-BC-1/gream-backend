package bc1.gream.domain.buy.controller;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCheckOrderResponseDto;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.gifticon.service.GifticonQueryService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buy/history")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserBoughtHistoryController {

    private final GifticonQueryService gifticonQueryService;
    private final BuyService buyService;

    @GetMapping("/end")
    public RestResponse<List<BuyCheckOrderResponseDto>> getBoughtOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return RestResponse.success(gifticonQueryService.getBoughtOrder(userDetails.getUser()));
    }


    @GetMapping("/onprogress")
    public RestResponse<List<BuyCheckBidResponseDto>> getBuyBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return RestResponse.success(buyService.findAllBuyBidCoupon(userDetails.getUser()));
    }

}
