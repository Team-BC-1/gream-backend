package bc1.gream.domain.product.service.query;

import bc1.gream.domain.product.entity.Product;
import java.util.List;

public interface ProductQueryService {

    // 전체 조회
    List<Product> findAll();

    // 상세 조회
    Product findBy(Long id);

    // 체결 거래 내역 조회 :: Order

    // 판매 입찰가 :: Order

    // 구매 입찰가 :: Order
}