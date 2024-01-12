package bc1.gream.domain.order.service;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.repository.SellRepository;
import bc1.gream.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;

    /**
     * Product에 대한 판매입찰가 내역 페이징 조회
     *
     * @param product  이모티콘 상품
     * @param pageable 페이징 요청 데이터
     * @return 판매입찰가 내역 페이징 데이터
     */
    @Transactional(readOnly = true)
    public Page<Sell> findAllSellBidsOf(Product product, Pageable pageable) {
        return sellRepository.findAllPricesOf(product, pageable);
    }
}
