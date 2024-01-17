package bc1.gream.test;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.jpa.AuditingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class, AuditingConfig.class})
public class BaseDataRepositoryTest implements ProductTest, UserTest, CouponTest, BuyTest, SellTest, OrderTest, GifticonTest {

    protected Product savedProduct;
    protected User savedSeller;
    protected User savedBuyer;
    protected Coupon savedCoupon;
    protected Order savedOrder;
    protected Gifticon savedGifticon;
    protected Sell savedSell;
    protected Buy savedBuy;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected BuyRepository buyRepository;
    @Autowired
    protected SellRepository sellRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected GifticonRepository gifticonRepository;

    /**
     * Data Repository Test 환경을 위해 setUp()에서 해당메서드를 호출해주어야 합니다.
     */
    protected void setUpBaseDataRepositoryTest() {
        savedProduct = productRepository.save(TEST_PRODUCT);
        savedBuyer = userRepository.save(TEST_BUYER);
        savedSeller = userRepository.save(TEST_SELLER);
        savedCoupon = couponRepository.save(
            Coupon.builder()
                .name(TEST_COUPON_NAME)
                .discountType(TEST_DISCOUNT_TYPE_WON)
                .discount(TEST_DISCOUNT)
                .status(TEST_COUPON_STATUS_AVAILABLE)
                .user(savedBuyer)
                .build());
        savedOrder = orderRepository.save(
            Order.builder()
                .product(savedProduct)
                .buyer(savedBuyer)
                .seller(savedSeller)
                .finalPrice(TEST_ORDER_FINAL_PRICE)
                .expectedPrice(TEST_ORDER_EXPECTED_PRICE)
                .build());
        savedGifticon = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(savedOrder)
                .build());
        savedSell = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(savedGifticon)
                .build()
        );
        savedBuy = buyRepository.save(
            Buy.builder()
                .price(TEST_BUY_PRICE)
                .deadlineAt(BuyTest.TEST_DEADLINE_AT)
                .user(savedBuyer)
                .product(savedProduct)
                .build()
        );
    }

    /**
     * {@link DataJpaTest} 는 롤백이 default라 무관하지만, 만약 롤백을 false로 하셨다면, tearDown()에서 해당메서드를 호출하여 저장된 데이터를 삭제합니다.
     */
    protected void tearDownBaseDataRepositoryTest() {
        buyRepository.deleteAllInBatch();
        sellRepository.deleteAllInBatch();
        gifticonRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }
}
