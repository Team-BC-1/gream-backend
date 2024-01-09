package bc1.gream.domain.order.service.query;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.entity.Product;
import java.util.List;

public interface OrderQueryService {

    List<Order> findAllTradesOf(Product product);
}
