package bc1.gream.domain.buy.controller;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.order.validator.ProductValidator;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
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
public class BuyBidController {

    private final BuyBidProvider buyBidProvider;
    private final ProductValidator productValidator;

    /**
     * 구매입찰 체결 요청
     *
     * @param userDetails 구매자
     * @param requestDto  가격, 입찰기간, 쿠폰아이디
     * @param productId   상품 아이디
     * @return 체결 결과 :: 가격, 구매입찰아이디
     */
    @PostMapping("/{productId}")
    @Operation(summary = "구매입찰 체결 요청", description = "상품에 대한 구매자의 구매입찰 체결요청을 처리합니다.")
    public RestResponse<BuyBidResponseDto> buyBidProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody BuyBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        Product product = productValidator.validateBy(productId);
        BuyBidResponseDto responseDto = buyBidProvider.buyBidProduct(userDetails.getUser(), requestDto, product);
        return RestResponse.success(responseDto);
    }

    /**
     * 구매입찰 취소 요청
     *
     * @param userDetails 구매자
     * @param buyId       구매입찰 아이디
     * @return 취소된 구매입찰 아이디
     */
    @DeleteMapping("/bid/{buyId}")
    @Operation(summary = "구매입찰 취소 요청", description = "상품에 대한 구매자의 구매입찰 취소요청을 처리합니다.")
    public RestResponse<BuyCancelBidResponseDto> buyCancelBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long buyId
    ) {
        BuyCancelBidResponseDto responseDto = buyBidProvider.buyCancelBid(userDetails.getUser(), buyId);
        return RestResponse.success(responseDto);
    }
}
