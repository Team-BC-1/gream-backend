package bc1.gream.domain.buy.service.query;

import static bc1.gream.global.common.ResultCase.BUY_BID_NOT_FOUND;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.mapper.BuyMapper;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.helper.CouponCalculator;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheNames = RedisCacheName.PRODUCTS_BUY)
public class BuyQueryService {

    private final BuyRepository buyRepository;

    public Buy findBuyById(Long buyId) {
        return buyRepository.findById(buyId).orElseThrow(
            () -> new GlobalException(BUY_BID_NOT_FOUND)
        );
    }

    /**
     * Product에 대한 구매입찰가 내역 페이징 조회
     *
     * @param product  이모티콘 상품
     * @param pageable 페이징 요청 데이터
     * @return 구매입찰가 내역 페이징 데이터
     */
    @Cacheable(key = "#product.id + #pageable.pageNumber")
    public Page<BuyPriceToQuantityResponseDto> findAllBuyBidsOf(Product product, Pageable pageable) {
        return new RestPage<>(buyRepository.findAllPriceToQuantityOf(product, pageable, LocalDateTime.now()));
    }

    /**
     * 해당상품과 가격에 대한 구매입찰을 가져옴
     *
     * @param productId 상품 아이디
     * @param price     구매를 원하는 상품 가격
     * @return 구매입찰
     */
    public Buy getRecentBuyBidOf(Long productId, Long price) {
        return buyRepository.findByProductIdAndPrice(productId, price, LocalDateTime.now())
            .orElseThrow(() -> new GlobalException(BUY_BID_NOT_FOUND));
    }

    /**
     * 구매자의 진행 중인 구매입찰에 대한 조회
     *
     * @param user 구매자
     * @return 진행 중인 구매입찰
     */
    public List<BuyCheckBidResponseDto> findAllBuyBidCoupon(User user) {
        List<BuyCheckBidResponseDto> buyCheckBidResponseDtos = buyRepository.findAllBuyBidCoupon(user, LocalDateTime.now());

        return buyCheckBidResponseDtos.stream()
            .map(bid -> BuyMapper.INSTANCE.toBuyCheckBidResponseDto(bid, getFinalPrice(bid.coupon(), bid.discountPrice())))
            .toList();
    }

    private Long getFinalPrice(Coupon coupon, Long discountPrice) {
        if (coupon == null) {
            return discountPrice;
        }

        return CouponCalculator.calculateDiscount(coupon, discountPrice);
    }
}