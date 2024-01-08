package bc1.gream.domain.product.service.command;

import bc1.gream.domain.user.entity.User;

public interface ProductCommendService {

    void likeProduct(User user, Long productId);

    void dislikeProduct(User user, Long productId);
}