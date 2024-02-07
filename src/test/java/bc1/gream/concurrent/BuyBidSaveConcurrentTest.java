package bc1.gream.concurrent;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.product.service.query.ProductQueryService;
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

@SpringBootTest
@WithMockCustomUser
@Disabled
@ActiveProfiles("test")
public class BuyBidSaveConcurrentTest extends BaseIntegrationTest {

    @Autowired
    private AsyncTransaction asyncTransaction;
    @Autowired
    private ProductQueryService productQueryService;
    @Autowired
    private BuyBidProvider buyBidProvider;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
        savedBuyer.increasePoint(10000000000L);
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
            .build();
        int threadCount = 2500;
        List<CompletableFuture<Void>> multipleTxs = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            var tx = CompletableFuture.runAsync(
                () -> {
                    asyncTransaction.run(() -> productQueryService.findAll());
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
}