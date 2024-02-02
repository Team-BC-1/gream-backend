package bc1.gream.deadlock;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.BaseIntegrationTest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@WithMockCustomUser
@Disabled
@ActiveProfiles("test")
public class BuyBidSaveOptimisticLockIssueTest extends BaseIntegrationTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
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
    @Transactional
    public void 스레드2500개_동시에_구매입찰생성요청() throws InterruptedException, ExecutionException {
        // GIVEN
        BuyBidRequestDto buyBidRequestDto = BuyBidRequestDto.builder()
            .price(1000L)
            .period(7)
            .build();
        int threadCount = 2500;
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // WHEN
        for (int i = 0; i < threadCount; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                () -> doCreateBuyOfBuyer(savedBuyer, buyBidRequestDto, savedIcedAmericano));
            futures.add(future);
        }

        // THEN
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            // Wait for all threads to finish within a specified timeout
            allOf.get(5, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            System.err.println("Thread Timeout : " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.err.println("Concurrency Issue : " + e.getMessage());
            e.printStackTrace();
        } catch (GlobalException e) {
            System.err.println("GlobalException Issue : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    void doCreateBuyOfBuyer(User buyer, BuyBidRequestDto requestDto, Product product) {
        try {
            // Introduce a timeout mechanism for the method call
            CompletableFuture<Void> productFuture = CompletableFuture.runAsync(() ->
                productService.findAll());
            productFuture.get(30, TimeUnit.SECONDS); // Adjust timeout as needed
            // Introduce a timeout mechanism for the method call
            CompletableFuture<Void> buyFuture = CompletableFuture.runAsync(() ->
                buyBidProvider.buyBidProduct(buyer, requestDto, product));
            buyFuture.get(30, TimeUnit.SECONDS); // Adjust timeout as needed
        } catch (InterruptedException | TimeoutException e) {
            System.err.println("Operation timed out or interrupted: " + e.getMessage());
            e.printStackTrace();
            // Log or handle the timeout issue here
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                System.err.println("Concurrency issue detected: " + cause.getMessage());
                cause.printStackTrace();
                // Log or handle the concurrency issue here
            } else {
                // Rethrow the exception if it's not a concurrency issue
                throw new RuntimeException("Unexpected exception", cause);
            }
        }
//        productService.findAll();
//        buyBidProvider.buyBidProduct(buyer, requestDto, product);
    }
}
