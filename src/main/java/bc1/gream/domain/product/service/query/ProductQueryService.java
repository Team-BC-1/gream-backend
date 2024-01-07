package bc1.gream.domain.product.service.query;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.unit.ProductCondition;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductQueryService {

    // 전체 조회
    List<Product> findAllBy();

    // 전체 조건 조회
    List<Product> findAllBy(ProductCondition condition);

    // 전체 조건 조회 페이징
    Page<Product> findAllByPaging(ProductCondition condition);

    // 상세 조회
    Product findBy(Long id);
}