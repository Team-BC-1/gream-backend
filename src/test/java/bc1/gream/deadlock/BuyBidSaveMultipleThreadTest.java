package bc1.gream.deadlock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.BaseIntegrationTest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@WithMockCustomUser
@Disabled
@ActiveProfiles("test")
public class BuyBidSaveMultipleThreadTest extends BaseIntegrationTest {

    private final AsyncTransaction asyncTransaction = new AsyncTransaction();
    @Autowired
    private ProductService productService;
    @Autowired
    private BuyBidProvider buyBidProvider;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseIntegrationTest();
    }

    @Test
    public void 스레드2500개_동시에_구매입찰생성요청() {
        // GIVEN
        BuyBidRequestDto buyBidRequestDto = BuyBidRequestDto.builder()
            .price(1000L)
            .period(7)
            .couponId(savedCoupon.getId())
            .build();
        int threadCount = 2500;
        List<CompletableFuture<Void>> multipleTxs = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            var tx = CompletableFuture.runAsync(
                () -> {
                    asyncTransaction.run(() -> productService.findAll());
                    asyncTransaction.run(() -> buyBidProvider.buyBidProduct(savedBuyer, buyBidRequestDto, savedIcedAmericano));
                });
            multipleTxs.add(tx);
        }

        // WHEN
        // THEN
        assertDoesNotThrow(() -> {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(multipleTxs.toArray(new CompletableFuture[0]));
            allOf.join();
        });
    }

    private static class AsyncTransaction {

        @Transactional
        public void run(Runnable runnable) {
            runnable.run();
        }
    }
}