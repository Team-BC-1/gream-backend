package bc1.gream.domain.sell.controller;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.ProductService;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.service.SellService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
public class SellController {

    private final SellService sellService;
    private final ProductService productService;

    @PostMapping("{productId}")
    public RestResponse<SellBidResponseDto> sellBidProduct(
        User user,
        @Valid @RequestBody SellBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        Product product = productService.findProductById(productId);
        SellBidResponseDto responseDto = sellService.sellBidProduct(user, requestDto, product);
        return RestResponse.success(responseDto);
    }
}
