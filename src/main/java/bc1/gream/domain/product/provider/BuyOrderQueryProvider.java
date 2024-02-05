package bc1.gream.domain.product.provider;

import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyOrderQueryProvider {

    private final ProductQueryService productQueryService;
    private final BuyQueryService buyQueryService;

    /**
     * 상품아이디값을 통한 상품 조회 이후, 해당 상품의 구매입찰 내역에 대한 페이징 데이터 반환
     *
     * @param productId 이모티콘 상품 id값
     * @param pageable  페이징 요청 입력
     * @return 해당 상품에 대한 구매입찰 내역
     * @author 임지훈
     */
    public List<BuyPriceToQuantityResponseDto> findAllBuyBidsOf(Long productId, Pageable pageable) {
        Product product = productQueryService.findBy(productId);
        return buyQueryService.findAllBuyBidsOf(product, pageable).getContent();
    }

    public Product findById(Long productId) {
        return productQueryService.findBy(productId);
    }
}
