package bc1.gream.domain.product.controller;

import bc1.gream.domain.product.dto.ProductLikeResponseDto;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.command.ProductCommendService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductLikeController {

    private final ProductCommendService productCommendService;

    @PostMapping("/{id}/like")
    public RestResponse<ProductLikeResponseDto> likeProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long productId
    ) {
        productCommendService.likeProduct(userDetails.getUser(), productId);
        ProductLikeResponseDto responseDto = ProductMapper.INSTANCE.toLikeResponseDto("관심상품 등록");
        return RestResponse.success(responseDto);
    }

    @DeleteMapping("/{id}/dislike")
    public RestResponse<ProductLikeResponseDto> dislikeProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long productId
    ) {
        productCommendService.dislikeProduct(userDetails.getUser(), productId);
        ProductLikeResponseDto responseDto = ProductMapper.INSTANCE.toLikeResponseDto("관심상품 등록 해제");
        return RestResponse.success(responseDto);
    }
}
