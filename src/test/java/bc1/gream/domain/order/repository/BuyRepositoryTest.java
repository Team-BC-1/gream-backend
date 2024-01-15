package bc1.gream.domain.order.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.repository.CouponRepository;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.jpa.AuditingConfig;
import bc1.gream.test.BuyTest;
import bc1.gream.test.CouponTest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class, AuditingConfig.class})
class BuyRepositoryTest implements BuyTest, CouponTest {

    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(TEST_USER);
        couponRepository.save(TEST_COUPON_FIX);
        productRepository.save(TEST_PRODUCT);
        insertBuyByCount(200);
    }

    @AfterEach
    void tearDown() {
        buyRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    private void insertBuyByCount(int buyCount) {
        // 입찰이 200개 있다고 치자.
        for (int i = 0; i < buyCount; i++) {
            Buy buy = Buy.builder()
                .price(TEST_BUY_PRICE)
                .deadlineAt(TEST_DEADLINE_AT)
                .user(TEST_USER)
                .product(TEST_PRODUCT)
                .couponId(TEST_COUPON_ID)
                .build();
            buyRepository.save(buy);
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void 동시에100개요청() {
        // GIVEN
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // WHEN
        List<Buy> foundBuys = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                Buy buy = buyRepository.findByProductIdAndPrice(TEST_PRODUCT_ID, TEST_BUY_PRICE)
                    .orElseThrow(() -> new GlobalException(ResultCase.BUY_BID_NOT_FOUND));
                foundBuys.add(buy);
//                try{
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
            });
        }

        // THEN
        List<Buy> uniqueBuys = foundBuys.stream()
            .distinct()
            .toList();
        assertEquals(foundBuys.size(), uniqueBuys.size());
    }
}