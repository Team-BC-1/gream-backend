package bc1.gream.domain.product.service.command;

import bc1.gream.domain.admin.dto.request.AdminProductRequestDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.infra.s3.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandService {

    private final ProductRepository productRepository;
    private final S3ImageService s3ImageService;

    public void addProduct(AdminProductRequestDto adminProductRequestDto) {
        verfiyExistenceByName(adminProductRequestDto);
        Product product = buildProductFromRequest(adminProductRequestDto);
        productRepository.save(product);
    }

    private void verfiyExistenceByName(AdminProductRequestDto adminProductRequestDto) {
        String productName = adminProductRequestDto.name();

        if (productRepository.existsByName(productName)) {
            throw new GlobalException(ResultCase.DUPLICATED_PRODUCT_NAME);
        }
    }

    private Product buildProductFromRequest(AdminProductRequestDto adminProductRequestDto) {
        String imageUrl = s3ImageService.getUrlAfterUpload(adminProductRequestDto.file());
        return Product.builder()
            .name(adminProductRequestDto.name())
            .brand(adminProductRequestDto.brand())
            .description(adminProductRequestDto.description())
            .imageUrl(imageUrl)
            .price(adminProductRequestDto.price())
            .build();
    }

}