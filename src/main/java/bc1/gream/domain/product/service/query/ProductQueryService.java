package bc1.gream.domain.product.service.query;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.unit.ProductCondition;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {

    // 전체 조회
    List<Product> findAll();

    // 전체 조건 조회
    List<Product> findAllBy(ProductCondition condition);

    // 전체 조건 조회 페이징
    Page<Product> findAllByPaging(ProductCondition condition, Pageable pageable);

    // 상세 조회
    Product findBy(Long id);
    // 상품 :: 체결 거래 내역 조회
    // 상품 :: 판매 입찰가 조회
    // 상품 :: 구매 입찰가 조회
}