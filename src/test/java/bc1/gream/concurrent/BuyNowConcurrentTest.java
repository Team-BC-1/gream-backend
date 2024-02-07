package bc1.gream.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.provider.BuyNowProvider;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
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
@Disabled
@ActiveProfiles("test")
public class BuyNowConcurrentTest extends BaseIntegrationTest {

    @Autowired
    private AsyncTransaction asyncTransaction;
    @Autowired
    private BuyNowProvider buyNowProvider;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseIntegrationTest();
    }

    @Test
    public void 동일한_판매입찰_동시에_즉시구매요청() {
        // GIVEN
        BuyNowRequestDto requestDto = BuyNowRequestDto.builder()
            .price(savedSell.getPrice())
            .couponId(savedCoupon.getId())
            .build();

        // WHEN
        int threadCount = 3;
        List<CompletableFuture<Void>> multipleTxs = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            var tx = CompletableFuture.runAsync(
                () -> asyncTransaction.run(
                    () -> {
                        // OSIV Filter 를 통해 로그인한 유저 조회
                        User buyer = userRepository.findByLoginId(savedBuyer.getLoginId())
                            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
                        buyNowProvider.buyNowProduct(buyer, requestDto, savedIcedAmericano.getId());
                    }));
            multipleTxs.add(tx);
        }

        // THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(multipleTxs.toArray(new CompletableFuture[0]));
            allOf.join();
        });
        exception.printStackTrace();
        assertEquals(ResultCase.SELL_BID_PRODUCT_NOT_FOUND.getMessage(), exception.getCause().getMessage());
    }
}