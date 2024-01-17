package bc1.gream.domain.product.controller;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.dto.response.ProductDislikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikesResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.command.ProductLikeService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import bc1.gream.global.validator.OrderCriteriaValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @PostMapping("/{id}/like")
    public RestResponse<ProductLikeResponseDto> likeProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long productId
    ) {
        productLikeService.likeProduct(userDetails.getUser(), productId);
        ProductLikeResponseDto responseDto = ProductMapper.INSTANCE.toLikeResponseDto("관심상품 등록");
        return RestResponse.success(responseDto);
    }

    @DeleteMapping("/{id}/dislike")
    public RestResponse<ProductDislikeResponseDto> dislikeProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long productId
    ) {
        productLikeService.dislikeProduct(userDetails.getUser(), productId);
        ProductDislikeResponseDto responseDto = ProductMapper.INSTANCE.toDislikeResponseDto("관심상품 등록 해제");
        return RestResponse.success(responseDto);
    }

    @GetMapping("/likes")
    public RestResponse<List<ProductLikesResponseDto>> productLikes(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Buy.class, pageable);
        List<Product> products = productLikeService.productLikes(userDetails.getUser(), pageable);
        List<ProductLikesResponseDto> response = products.stream().map(ProductMapper.INSTANCE::toProductLikesResponseDto).toList();
        return RestResponse.success(response);
    }
}
