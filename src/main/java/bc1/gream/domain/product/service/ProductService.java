package bc1.gream.domain.product.service;

import static bc1.gream.global.common.ResultCase.PRODUCT_NOT_FOUND;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new GlobalException(PRODUCT_NOT_FOUND)
        );
    }
}
