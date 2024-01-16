package bc1.gream.domain.buy.service;

import static bc1.gream.global.common.ResultCase.BUY_BID_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.GIFTICON_NOT_FOUND;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.coupon.entity.Coupon;
import bc1.gream.domain.user.coupon.entity.DiscountType;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
import bc1.gream.global.exception.GlobalException;
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


    public Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, 7);
    }

    public Buy findBuyById(Long buyId) {
        return buyRepository.findById(buyId).orElseThrow(
            () -> new GlobalException(BUY_BID_NOT_FOUND)
        );
    }

    public boolean isBuyerLoggedInUser(Buy buy, User user) {
        return buy.getUser().getLoginId().equals(user.getLoginId());
    }

    public Long calcDiscount(Long couponId, Long price, User user) {
        if (couponId == null) {
            return price;
        }
        Coupon coupon = couponService.findCouponById(couponId, user);

        if (coupon.getDiscountType().equals(DiscountType.FIX)) {
            return price - coupon.getDiscount();
        }
        return price * (100 - coupon.getDiscount()) / 100;
    }

    public void orderGifticonBySellId(Long sellId, Order order) {
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

    /**
     * 해당상품과 가격에 대한 구매입찰을 가져옴
     *
     * @param productId 상품 아이디
     * @param price     구매를 원하는 상품 가격
     * @return 구매입찰
     */
    @Transactional(readOnly = true)
    public Buy getRecentBuyBidOf(Long productId, Long price) {
        return buyRepository.findByProductIdAndPrice(productId, price)
            .orElseThrow(() -> new GlobalException(BUY_BID_NOT_FOUND));
    }

    @Transactional
    public void delete(Buy buy) {
        buyRepository.delete(buy);
    }
}