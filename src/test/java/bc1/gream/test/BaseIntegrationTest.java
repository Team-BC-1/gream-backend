package bc1.gream.test;

import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.order.repository.SellRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.CouponRepository;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.security.WithMockCustomUser;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@WithMockCustomUser
@ActiveProfiles("test")
@Rollback
@Disabled("통합테스트는 로컬에서만 실행합니다. 실행 시, SECRET KEY 에 대한 IntelliJ 환경변수를 설정해주어야 합니다.")
public class BaseIntegrationTest implements ProductTest, UserTest, CouponTest, BuyTest, SellTest, OrderTest, GifticonTest {

    protected Product savedProduct;
    protected User savedSeller;
    protected User savedBuyer;
    protected Coupon savedCoupon;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private SellRepository sellRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GifticonRepository gifticonRepository;

    protected void setUpBaseIntegrationTest() {
        savedProduct = productRepository.save(TEST_PRODUCT);
        savedBuyer = userRepository.save(TEST_BUYER);
        savedSeller = userRepository.save(TEST_SELLER);
        Coupon couponOfBuyer = Coupon.builder()
            .name(TEST_COUPON_NAME)
            .discountType(TEST_DISCOUNT_TYPE_WON)
            .discount(TEST_DISCOUNT)
            .status(TEST_COUPON_STATUS_AVAILABLE)
            .user(savedBuyer)
            .build();
        savedCoupon = couponRepository.save(couponOfBuyer);
    }

    protected void tearDownBaseIntegrationTest() {
        sellRepository.deleteAllInBatch();
        buyRepository.deleteAllInBatch();
        gifticonRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }
}
