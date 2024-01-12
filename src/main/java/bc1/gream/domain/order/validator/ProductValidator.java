package bc1.gream.domain.order.validator;

import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validateBy(Long productId) {
        productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND));
    }
}
