package bc1.gream.domain.product.service.command;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLikeService {


    private final ProductRepository productRepository;

    public void likeProduct(User user, Long productId) {
        Product product = getProductBy(productId);
        user.addLikeProduct(product);
    }

    public void dislikeProduct(User user, Long productId) {
        Product product = getProductBy(productId);
        user.removeLikeProduct(product);
    }

    private Product getProductBy(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND));
    }
}