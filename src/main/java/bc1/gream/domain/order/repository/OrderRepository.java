package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByProductOrderByCreatedAtDesc(Product product);
}
