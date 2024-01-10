package bc1.gream.domain.product.repository;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.unit.ProductCondition;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    // 전체 조회
    List<Product> findAllBy(ProductCondition condition);

    // 전체 조회
    Page<Product> findAllByPaging(ProductCondition condition, Pageable pageable);
}