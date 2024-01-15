package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellRepositoryCustom {

    Page<Sell> findAllPricesOf(Product product, Pageable pageable);

    Optional<Sell> findByProductIdAndPrice(Long productId, Long price);
}
