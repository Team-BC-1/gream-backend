package bc1.gream.domain.product.controller;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.dto.response.ProductDislikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikesResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.command.ProductLikeCommandService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import bc1.gream.global.validator.OrderCriteriaValidator;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/products")
public class ProductLikeController {

    private final ProductLikeCommandService productLikeCommandService;

    /**
     * 상품 좋아요 요청
     *
     * @param userDetails 사용자
     * @param productId   상품 아이디
     * @return 결과 메세지
     */
    @PostMapping("/{id}/like")
    @Operation(summary = "관심상품 등록 요청", description = "사용자의 관심상품 등록요청을 처리합니다.")
    public RestResponse<ProductLikeResponseDto> likeProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long productId
    ) {
        productLikeCommandService.likeProduct(userDetails.getUser(), productId);
        ProductLikeResponseDto responseDto = ProductMapper.INSTANCE.toLikeResponseDto("관심상품 등록");
        return RestResponse.success(responseDto);
    }

    /**
     * 상품 싫어요 요청
     *
     * @param userDetails 사용자
     * @param productId   상품 아이디
     * @return 결과 메세지
     */
    @DeleteMapping("/{id}/dislike")
    @Operation(summary = "관심상품 해제 요청", description = "사용자의 관심상품 해제요청을 처리합니다.")
    public RestResponse<ProductDislikeResponseDto> dislikeProduct(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long productId
    ) {
        productLikeCommandService.dislikeProduct(userDetails.getUser(), productId);
        ProductDislikeResponseDto responseDto = ProductMapper.INSTANCE.toDislikeResponseDto("관심상품 등록 해제");
        return RestResponse.success(responseDto);
    }

    /**
     * 좋아요 누른 상품 목록 조회 요청
     *
     * @param userDetails 사용자
     * @param pageable    페이지 요청
     * @return 좋아요 누른 상품 정보 목록
     */
    @GetMapping("/likes")
    @Operation(summary = "관심상품 조회 요청", description = "사용자의 모든 관심상품 조회요청을 처리합니다.")
    public RestResponse<List<ProductLikesResponseDto>> productLikes(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Buy.class, pageable);
        List<Product> products = productLikeCommandService.productLikes(userDetails.getUser(), pageable);
        List<ProductLikesResponseDto> response = products.stream().map(ProductMapper.INSTANCE::toProductLikesResponseDto).toList();
        return RestResponse.success(response);
    }
}
