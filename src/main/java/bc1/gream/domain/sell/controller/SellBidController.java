package bc1.gream.domain.sell.controller;

import bc1.gream.domain.order.validator.ProductValidator;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellCancelBidResponseDto;
import bc1.gream.domain.sell.provider.SellBidProvider;
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
@RequestMapping("/api/sell")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SellBidController {

    private final SellBidProvider sellBidProvider;
    private final ProductValidator productValidator;

    /**
     * 판매입찰 체결 요청
     *
     * @param userDetails 판매자
     * @param requestDto  가격, 입찰기간, 기프티콘 이미지
     * @param productId   상품 아이디
     * @return 판매입찰가격, 판매입찰 아이디
     */
    @PostMapping("/{productId}")
    public RestResponse<SellBidResponseDto> createSellBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody SellBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        Product product = productValidator.validateBy(productId);
        SellBidResponseDto responseDto = sellBidProvider.createSellBid(userDetails.getUser(), requestDto, product);
        return RestResponse.success(responseDto);
    }

    /**
     * 판매입찰 취소 요청
     *
     * @param userDetails 판매자
     * @param sellId      입찰 아이디
     * @return 취소된 입찰 아이디
     */
    @DeleteMapping("/bid/{sellId}")
    public RestResponse<SellCancelBidResponseDto> cancelSellBid(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long sellId
    ) {
        SellCancelBidResponseDto responseDto = sellBidProvider.sellCancelBid(userDetails.getUser(), sellId);
        return RestResponse.success(responseDto);
    }
}
