package bc1.gream.domain.sell.service.query;

import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.product.dto.response.SellPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.redis.RedisCacheName;
import bc1.gream.global.redis.RestPage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@CacheConfig(cacheNames = RedisCacheName.PRODUCTS_SELL)
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
    @Cacheable(key = "#product.id + #pageable.pageNumber")
    public Page<SellPriceToQuantityResponseDto> findAllSellBidsOf(Product product, Pageable pageable) {
        return new RestPage<>(sellRepository.findAllPriceToQuantityOf(product, pageable, LocalDateTime.now()));
    }


    @Transactional(readOnly = true)
    public Sell findByIdAndUser(Long sellId, User user) {
        return sellRepository.findByIdAndUser(sellId, user).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Retryable(backoff = @Backoff(delay = 100))
    public Sell getRecentSellBidof(Long productId, Long price) {
        return sellRepository.findByProductIdAndPrice(productId, price, LocalDateTime.now()).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
    }

    /**
     * 진행 중인 판매자의 판매입찰 내역 조회
     *
     * @param seller 판매자
     * @return 마감기한 이전의 진행 중인 판매입찰 리스트
     */
    public List<Sell> getUserSellOnProgressOf(User seller) {
        return sellRepository.findAllByUserAndDeadlineAtGreaterThan(seller, LocalDateTime.now());
    }
}
