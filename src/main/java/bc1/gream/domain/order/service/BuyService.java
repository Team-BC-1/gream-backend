package bc1.gream.domain.order.service;

import static bc1.gream.global.common.ResultCase.BUY_BID_PRODUCT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.GIFTICON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;
import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.order.dto.request.BuyBidRequestDto;
import bc1.gream.domain.order.dto.request.BuyNowRequestDto;
import bc1.gream.domain.order.dto.response.BuyBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyNowResponseDto;
import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.mapper.BuyMapper;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.repository.BuyRepository;
import bc1.gream.domain.order.repository.GifticonRepository;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.order.repository.SellRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.DiscountType;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
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

    private final CouponService couponService;
    private final ProductService productService;

    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;
    private final OrderRepository orderRepository;
    private final GifticonRepository gifticonRepository;

    public BuyBidResponseDto buyBidProduct(User user, BuyBidRequestDto requestDto, Long productId) {
        Long price = requestDto.price();
        Integer period = getPeriod(requestDto.period());
        Long couponId = requestDto.couponId();
        LocalDate date = LocalDate.now();
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(period);
        Product product = productService.findBy(productId);

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

        if (isBuyerLoggedInUser(buyBid, user)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        buyRepository.delete(buyBid);

        return new BuyCancelBidResponseDto();
    }

    public BuyNowResponseDto buyNowProduct(User user, BuyNowRequestDto requestDto, Long productId) {
        Long price = requestDto.price();    // finalPrice
        Sell sell = sellRepository.findByProductIdAndPrice(productId, price).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
        Long expectedPrice = calcDiscount(requestDto, user);

        Order order = Order.builder()
            .product(sell.getProduct())
            .buyer(user)
            .seller(sell.getUser())
            .finalPrice(price)
            .expectedPrice(expectedPrice)
            .build();

        Order savedOrder = orderRepository.save(order);
        orderGifticon(sell.getId(), savedOrder);

        return OrderMapper.INSTANCE.toBuyNowResponseDto(savedOrder);
    }

    private Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, 7);
    }

    public Buy findBuyById(Long buyId) {
        return buyRepository.findById(buyId).orElseThrow(
            () -> new GlobalException(BUY_BID_PRODUCT_NOT_FOUND)
        );
    }

    private boolean isBuyerLoggedInUser(Buy buy, User user) {
        return buy.getUser().getLoginId().equals(user.getLoginId());
    }

    private Long calcDiscount(BuyNowRequestDto requestDto, User user) {
        Coupon coupon = couponService.findCouponById(requestDto.couponId(), user);
        Long expectedPrice = 0L;

        if (coupon.getDiscountType().equals(DiscountType.FIX)) {
            expectedPrice = requestDto.price() - coupon.getDiscount();
        } else {
            expectedPrice = requestDto.price() * (100 - coupon.getDiscount()) / 100;
        }

        return expectedPrice;
    }

    private void orderGifticon(Long sellId, Order order) {
        Gifticon gifticon = gifticonRepository.findBySell_Id(sellId).orElseThrow(
            () -> new GlobalException(GIFTICON_NOT_FOUND)
        );

        gifticon.updateOrder(order);
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