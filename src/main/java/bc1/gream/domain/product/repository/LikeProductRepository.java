package bc1.gream.domain.product.repository;

import bc1.gream.domain.product.entity.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {

}