package bc1.gream.domain.product.service.query;

import bc1.gream.domain.order.entity.Order;
import java.util.List;

public interface ProductOrderQueryService {

    List<Order> findAllTradesOf(Long productId);
}
