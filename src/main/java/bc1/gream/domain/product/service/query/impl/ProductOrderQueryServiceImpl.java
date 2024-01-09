package bc1.gream.domain.product.service.query.impl;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductOrderQueryService;
import bc1.gream.domain.product.service.query.ProductQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderQueryServiceImpl implements ProductOrderQueryService {

    private final ProductQueryService productQueryService;
    private final OrderQueryService orderQueryService;

    @Override
    public List<Order> findAllTradesOf(Long productId) {
        Product product = productQueryService.findBy(productId);
        return orderQueryService.findAllTradesOf(product);
    }
}
