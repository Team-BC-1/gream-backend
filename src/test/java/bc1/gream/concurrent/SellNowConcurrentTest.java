package bc1.gream.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.provider.SellNowProvider;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@WithMockCustomUser
@Disabled
@ActiveProfiles("test")
public class SellNowConcurrentTest extends BaseIntegrationTest {

    @Autowired
    private AsyncTransaction asyncTransaction;
    @Autowired
    private SellNowProvider sellNowProvider;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseIntegrationTest();
    }

    @Test
    public void 동일한_판매입찰_동시에_구매입찰생성요청() {
        // GIVEN
        SellNowRequestDto sellNowRequestDto = SellNowRequestDto.builder()
            .price(savedBuy.getPrice())
            .file(new MockMultipartFile(
                "file",
                "emptyFile.png",
                "image/png",
                new byte[0]
            ))
            .build();

        // WHEN
        int threadCount = 3;
        List<CompletableFuture<Void>> multipleTxs = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            var tx = CompletableFuture.runAsync(
                () -> asyncTransaction.run(
                    () -> {
                        // OSIV Filter 를 통해 로그인한 유저 조회
                        User seller = userRepository.findByLoginId(savedSeller.getLoginId())
                            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
                        sellNowProvider.sellNowProduct(seller, sellNowRequestDto, savedIcedAmericano.getId());
                    }));
            multipleTxs.add(tx);
        }

        // THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(multipleTxs.toArray(new CompletableFuture[0]));
            allOf.join();
        });
        assertEquals(ResultCase.BUY_BID_NOT_FOUND.getMessage(), exception.getCause().getMessage());
    }
}