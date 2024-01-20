package bc1.gream.domain.sell.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.provider.SellNowProvider;
import bc1.gream.test.BaseIntegrationTest;
import bc1.gream.test.BuyTest;
import bc1.gream.test.CouponTest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled("통합테스트는 로컬에서만 실행합니다. 실행 시, SECRET KEY 에 대한 IntelliJ 환경변수를 설정해주어야 합니다.")
class SellNowProviderIntegrationTest extends BaseIntegrationTest implements BuyTest, CouponTest {

    @Autowired
    private SellNowProvider sellNowProvider;
    @Autowired
    private BuyRepository buyRepository;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
        insertBuyByCount(200);  // 입찰이 200개 있다고 치자.
    }

    private void insertBuyByCount(int buyCount) {
        for (int i = 0; i < buyCount; i++) {
            Buy buy = Buy.builder()
                .price(TEST_BUY_PRICE)
                .deadlineAt(BuyTest.TEST_DEADLINE_AT)
                .user(savedBuyer)
                .product(savedIcedAmericano)
                .couponId(savedCoupon.getId())
                .build();
            buyRepository.save(buy);
        }
    }

    @Test
    public void 즉시판매조회() {
        // GIVEN
        SellNowRequestDto requestDto = new SellNowRequestDto(TEST_BUY_PRICE, TEST_GIFTICON_FILE);

        // WHEN
        SellNowResponseDto responseDto = sellNowProvider.sellNowProduct(savedSeller, requestDto, savedIcedAmericano.getId());

        // THEN
        System.out.println("responseDto = " + responseDto);
    }

    @Test
    public void 즉시판매조회_동시에100개요청() {
        // GIVEN
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        SellNowRequestDto requestDto = new SellNowRequestDto(TEST_BUY_PRICE, TEST_GIFTICON_FILE);

        // WHEN
        List<SellNowResponseDto> sellNowResponseDtos = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                SellNowResponseDto responseDto = sellNowProvider.sellNowProduct(savedSeller, requestDto, savedIcedAmericano.getId());
                sellNowResponseDtos.add(responseDto);
//                try{
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                } finally {
//                    countDownLatch.countDown();
//                }
            });
        }

        // THEN
        List<SellNowResponseDto> uniqueResponseDtos = sellNowResponseDtos.stream()
            .distinct()
            .toList();
        assertEquals(sellNowResponseDtos.size(), uniqueResponseDtos.size());
    }
}