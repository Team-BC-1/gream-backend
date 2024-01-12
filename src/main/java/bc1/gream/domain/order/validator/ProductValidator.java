package bc1.gream.domain.order.validator;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    /**
     * 상품이 존재하는지 검증, 없다면 예외처리
     *
     * @param productId 상품아이디
     * @return 상품
     * @throws GlobalException if 상품 does not exist
     */
    public Product validateBy(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND));
    }
}
