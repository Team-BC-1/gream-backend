package bc1.gream.domain.product.service.query;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.product.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOrderQueryService {


    private final ProductQueryService productQueryService;
    private final OrderQueryService orderQueryService;

    public List<Order> findAllTradesOf(Long productId) {
        Product product = productQueryService.findBy(productId);
        return orderQueryService.findAllTradesOf(product);
    }
}
