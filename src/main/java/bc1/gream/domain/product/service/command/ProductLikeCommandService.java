package bc1.gream.domain.product.service.command;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.LikeProductRepository;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductLikeCommandService {


    private final ProductRepository productRepository;
    private final LikeProductRepository likeProductRepository;

    public void likeProduct(User user, Long productId) {
        Product product = getProductBy(productId);
        boolean hasNotLikedThisProduct = user.getLikeProducts().stream()
            .noneMatch(likeProduct -> likeProduct.getProduct().equals(product));
        if (hasNotLikedThisProduct) {
            user.addLikeProduct(product);
        }
    }

    public void dislikeProduct(User user, Long productId) {
        Product product = getProductBy(productId);
        user.removeLikeProduct(product);
    }

    private Product getProductBy(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND));
    }

    public List<Product> productLikes(User user, Pageable pageable) {
        return likeProductRepository.findByUserID(user, pageable);
    }
}