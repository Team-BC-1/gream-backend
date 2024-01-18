package bc1.gream.test;

import static bc1.gream.domain.coupon.entity.CouponStatus.AVAILABLE;
import static bc1.gream.domain.coupon.entity.DiscountType.FIX;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.entity.DiscountType;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
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
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

//@ActiveProfiles("test")
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
        savedProduct = saveProduct(TEST_PRODUCT);
        savedBuyer = saveBuyer(TEST_BUYER);
        savedSeller = saveSeller(TEST_SELLER);
        savedCoupon = saveCouponOf(savedBuyer);
        savedOrder = saveOrderOf(savedProduct, savedBuyer, savedSeller);
        savedGifticon = saveGifticonOf(savedOrder);
        savedSell = saveSellOf(savedProduct, savedSeller, savedGifticon);
        savedBuy = saveBuyOf(savedBuyer, savedProduct);
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


    private Buy saveBuyOf(User savedBuyer, Product savedProduct) {
        Long TEST_BUY_PRICE = 4_500L;
        LocalDateTime TEST_DEADLINE_AT = LocalDateTime.now().plusDays(7);
        Buy buy = Buy.builder()
            .price(TEST_BUY_PRICE)
            .deadlineAt(TEST_DEADLINE_AT)
            .user(savedBuyer)
            .product(savedProduct)
            .build();
        ReflectionTestUtils.setField(buy, "id", 2000L);
        return buyRepository.save(buy);
    }

    private Sell saveSellOf(Product savedProduct, User savedSeller, Gifticon savedGifticon) {
        Long TEST_SELL_PRICE = 4_500L;
        LocalDateTime TEST_DEADLINE_AT = LocalDateTime.now().plusDays(7);

        Sell sell = Sell.builder()
            .price(TEST_SELL_PRICE)
            .deadlineAt(TEST_DEADLINE_AT)
            .product(savedProduct)
            .user(savedSeller)
            .gifticon(savedGifticon)
            .build();
        ReflectionTestUtils.setField(sell, "id", 2000L);
        return sellRepository.save(sell);
    }

    private Gifticon saveGifticonOf(Order savedOrder) {
        String TEST_GIFTICON_URL = "images/images.png";

        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(TEST_GIFTICON_URL)
            .order(savedOrder)
            .build();
        ReflectionTestUtils.setField(gifticon, "id", 2000L);
        return gifticonRepository.save(gifticon);
    }

    private Order saveOrderOf(Product savedProduct, User savedBuyer, User savedSeller) {
        Long TEST_ORDER_FINAL_PRICE = 4_000L;
        Long TEST_ORDER_EXPECTED_PRICE = 4_500L;

        Order order = Order.builder()
            .product(savedProduct)
            .buyer(savedBuyer)
            .seller(savedSeller)
            .finalPrice(TEST_ORDER_FINAL_PRICE)
            .expectedPrice(TEST_ORDER_EXPECTED_PRICE)
            .build();
        ReflectionTestUtils.setField(order, "id", 2000L);
        return orderRepository.save(order);
    }

    Coupon saveCouponOf(User savedBuyer) {
        String TEST_COUPON_NAME = "TEST COUPON";
        Long TEST_DISCOUNT = 500L;
        DiscountType TEST_DISCOUNT_TYPE_WON = FIX;
        CouponStatus TEST_COUPON_STATUS_AVAILABLE = AVAILABLE;

        Coupon coupon = Coupon.builder()
            .name(TEST_COUPON_NAME)
            .discountType(TEST_DISCOUNT_TYPE_WON)
            .discount(TEST_DISCOUNT)
            .status(TEST_COUPON_STATUS_AVAILABLE)
            .user(savedBuyer)
            .build();
        ReflectionTestUtils.setField(coupon, "id", 2000L);
        return couponRepository.save(coupon);
    }

    private Product saveProduct(Product product) {
        ReflectionTestUtils.setField(product, "id", 2000L);
        return productRepository.save(product);
    }

    User saveBuyer(User user) {
        ReflectionTestUtils.setField(user, "id", 2000L);
        return userRepository.saveAndFlush(user);
    }

    User saveSeller(User user) {
        ReflectionTestUtils.setField(user, "id", 2001L);
        return userRepository.saveAndFlush(user);
    }
}