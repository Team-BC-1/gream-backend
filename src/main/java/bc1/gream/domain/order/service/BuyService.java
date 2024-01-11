package bc1.gream.domain.order.service;

import static bc1.gream.global.common.ResultCase.BUY_BID_PRODUCT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;
import static bc1.gream.global.common.ResultCase.PRODUCT_NOT_FOUND;

import bc1.gream.domain.order.dto.request.BuyBidRequestDto;
import bc1.gream.domain.order.dto.response.BuyBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.mapper.BuyMapper;
import bc1.gream.domain.order.repository.BuyRepository;
import bc1.gream.domain.order.repository.GifticonRepository;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.order.repository.SellRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.CouponRepository;
import bc1.gream.global.exception.GlobalException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final BuyRepository buyRepository;
    private final ProductRepository productRepository;
    private final SellRepository sellRepository;
    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final GifticonRepository gifticonRepository;

    public BuyBidResponseDto buyBidProduct(User user, BuyBidRequestDto requestDto, Long productId) {
        Long price = requestDto.price();
        Integer period = getPeriod(requestDto.period());
        Long couponId = requestDto.couponId();
        LocalDate date = LocalDate.now();
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(period);
        Product product = findProductById(productId);

        Buy buy = Buy.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .couponId(couponId)
            .user(user)
            .product(product)
            .build();

        Buy savedBuy = buyRepository.save(buy);

        return BuyMapper.INSTANCE.toBuyBidResponseDto(savedBuy);
    }


    public BuyCancelBidResponseDto buyCancelBid(User user, Long buyId) {
        Buy buyBid = findBuyById(buyId);

        if (isNotBuyerLoggedInUser(buyBid, user)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        buyRepository.delete(buyBid);

        return new BuyCancelBidResponseDto();
    }

    private Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, 7);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new GlobalException(PRODUCT_NOT_FOUND)
        );
    }

    public Buy findBuyById(Long buyId) {
        return buyRepository.findById(buyId).orElseThrow(
            () -> new GlobalException(BUY_BID_PRODUCT_NOT_FOUND)
        );
    }

    private boolean isNotBuyerLoggedInUser(Buy buy, User user) {
        return !buy.getUser().getLoginId().equals(user.getLoginId());
    }


    /**
     * Product에 대한 구매입찰가 내역 페이징 조회
     *
     * @param product  이모티콘 상품
     * @param pageable 페이징 요청 데이터
     * @return 구매입찰가 내역 페이징 데이터
     */
    @Transactional(readOnly = true)
    public Page<Buy> findAllBuyBidsOf(Product product, Pageable pageable) {
        return buyRepository.findAllPricesOf(product, pageable);
    }
}