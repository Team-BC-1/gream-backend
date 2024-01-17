package bc1.gream.domain.buy.controller;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.order.validator.ProductValidator;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Bearer Authentication")
public class BuyBidController {

    private final BuyBidProvider buyBidProvider;
    private final ProductValidator productValidator;

    /**
     * 구매 입찰 생성
     */
    @PostMapping("/{productId}")
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
     * 구매 입찰 삭제
     */
    @DeleteMapping("/bid/{buyId}")
    public RestResponse<BuyCancelBidResponseDto> buyCancelBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long buyId
    ) {
        BuyCancelBidResponseDto responseDto = buyBidProvider.buyCancelBid(userDetails.getUser(), buyId);
        return RestResponse.success(responseDto);
    }
}
