package bc1.gream.domain.product.repository;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface LikeProductRepositoryCustom {

    List<Product> findByUserID(User user, Pageable pageable);
}
