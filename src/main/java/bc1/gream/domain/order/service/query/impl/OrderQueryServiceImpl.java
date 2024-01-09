package bc1.gream.domain.order.service.query.impl;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.product.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAllTradesOf(Product product) {
        return orderRepository.findAllByProduct(product);
    }
}
