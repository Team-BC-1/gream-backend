package bc1.gream.domain.buy.controller;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCheckOrderResponseDto;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buy/history")
@RequiredArgsConstructor
public class UserBoughtHistoryController {

    private final OrderQueryService orderQueryService;
    private final BuyQueryService buyQueryService;

    @GetMapping("/end")
    @Operation(summary = "구매자 구매완료상품 조회 요청", description = "구매자의 구매완료한 상품 조회요청을 처리합니다.")
    public RestResponse<List<BuyCheckOrderResponseDto>> getBoughtOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return RestResponse.success(orderQueryService.findAllBoughtOrder(userDetails.getUser()));
    }


    @GetMapping("/onprogress")
    @Operation(summary = "구매자 진행 중인 구매입찰 조회 요청", description = "구매자의 진행 중인 구매입찰에 대한 조회요청을 처리합니다.")
    public RestResponse<List<BuyCheckBidResponseDto>> getBuyBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return RestResponse.success(buyQueryService.findAllBuyBidCoupon(userDetails.getUser()));
    }

}
