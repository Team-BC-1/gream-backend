package bc1.gream.domain.product.controller.command;

import bc1.gream.domain.product.dto.ProductLikeResponseDto;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.command.ProductCommendService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductCommendController {

    private final ProductCommendService productCommendService;

    @PostMapping("/{id}/like")
    public RestResponse<ProductLikeResponseDto> likeProduct(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long productId
    ) {
        // userDetails 를 통해 userService를 사용하여 user를 가져와야함
        User user = User.builder().build();
        productCommendService.likeProduct(user, productId);
        ProductLikeResponseDto responseDto = ProductMapper.INSTANCE.toLikeResponseDto("관심상품 등록");
        return RestResponse.success(responseDto);
    }

    @DeleteMapping("/{id}/dislike")
    public RestResponse<ProductLikeResponseDto> dislikeProduct(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long productId
    ) {
        // userDetails 를 통해 userService를 사용하여 user를 가져와야함
        User user = User.builder().build();
        productCommendService.dislikeProduct(user, productId);
        ProductLikeResponseDto responseDto = ProductMapper.INSTANCE.toLikeResponseDto("관심상품 등록 해제");
        return RestResponse.success(responseDto);
    }
}
