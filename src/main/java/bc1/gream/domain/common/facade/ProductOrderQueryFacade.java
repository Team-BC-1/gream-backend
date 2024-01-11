package bc1.gream.domain.common.facade;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderQueryFacade {

    private final ProductService productService;
    private final OrderQueryService orderQueryService;

    /**
     * 상품아이디값을 통한 상품 조회 이후, 해당 상품의 모든 거래내역 반환
     *
     * @param productId 이모티콘 상품 id값
     * @return 해당 상품에 대한 거래내역
     * @author 임지훈
     */
    public List<Order> findAllTradesOf(Long productId) {
        Product product = productService.findBy(productId);
        return orderQueryService.findAllTradesOf(product);
    }
}