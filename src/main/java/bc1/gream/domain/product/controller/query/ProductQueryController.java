package bc1.gream.domain.product.controller.query;

import bc1.gream.domain.product.dto.ProductQueryResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.global.common.RestResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/* 읽기 전용 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public RestResponse<List<ProductQueryResponseDto>> findAll() {
        List<Product> products = productQueryService.findAll();
        List<ProductQueryResponseDto> responseDtos = products.stream()
            .map(ProductMapper.INSTANCE::toQueryResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }

    @GetMapping("/{id}")
    public RestResponse<ProductQueryResponseDto> findAllBy(
        @PathVariable("id") Long productId
    ) {
        Product product = productQueryService.findBy(productId);
        ProductQueryResponseDto responseDto = ProductMapper.INSTANCE.toQueryResponseDto(product);
        return RestResponse.success(responseDto);
    }
}