package bc1.gream.domain.buy.repository;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuyRepositoryCustom {

    Page<Buy> findAllPricesOf(Product product, Pageable pageable);
}
