package bc1.gream.domain.admin.controller;

import bc1.gream.domain.admin.dto.AdminProductRequestDto;
import bc1.gream.domain.admin.dto.AdminProductResponseDto;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.global.common.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductService productService;

    @PostMapping("/products")
    public RestResponse<AdminProductResponseDto> addProducts(
        @RequestBody AdminProductRequestDto adminProductRequestDto
    ) {
        productService.addProduct(adminProductRequestDto);
        return RestResponse.success(new AdminProductResponseDto());
    }
}
