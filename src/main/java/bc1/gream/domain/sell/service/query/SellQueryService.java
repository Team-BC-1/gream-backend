package bc1.gream.domain.sell.service.query;

import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.product.dto.response.SellPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellQueryService {

    private final SellRepository sellRepository;

    /**
     * Product에 대한 판매입찰가 내역 페이징 조회
     *
     * @param product  이모티콘 상품
     * @param pageable 페이징 요청 데이터
     * @return 판매입찰가 내역 페이징 데이터
     */
    @Transactional(readOnly = true)
    public Page<SellPriceToQuantityResponseDto> findAllSellBidsOf(Product product, Pageable pageable) {
        return sellRepository.findAllPriceToQuantityOf(product, pageable);
    }


    @Transactional(readOnly = true)
    public Sell findByIdAndUser(Long sellId, User user) {
        return sellRepository.findByIdAndUser(sellId, user).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    public Sell getRecentSellBidof(Long productId, Long price) {
        return sellRepository.findByProductIdAndPrice(productId, price).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
    }

    public List<Sell> getUserSellOnProgressOf(User seller) {
        return sellRepository.findAllByUser(seller);
    }
}
