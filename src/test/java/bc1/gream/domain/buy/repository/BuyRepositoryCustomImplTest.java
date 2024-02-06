package bc1.gream.domain.buy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.test.BaseDataRepositoryTest;
import bc1.gream.test.BuyTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class BuyRepositoryCustomImplTest extends BaseDataRepositoryTest implements BuyTest {

    @Autowired
    private BuyRepositoryCustomImpl buyRepositoryCustom;
    @Autowired
    private BuyRepository buyRepository;

    @BeforeEach
    void setUp() {
        setUpBaseDataRepositoryTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseDataRepositoryTest();
    }

    @Test
    public void 상품_구매입찰가격수량_조회페이징_기본정렬_가격내림차순() {
        // GIVEN
        Buy samePriceBuyBid = buyRepository.save(
            Buy.builder()
                .price(TEST_BUY_PRICE)
                .deadlineAt(BuyTest.TEST_DEADLINE_AT)
                .user(savedBuyer)
                .product(savedProduct)
                .build()
        );
        Buy expensiveBuyBid = buyRepository.save(
            Buy.builder()
                .price(TEST_BUY_PRICE + 100000L)
                .deadlineAt(BuyTest.TEST_DEADLINE_AT)
                .user(savedBuyer)
                .product(savedProduct)
                .build()
        );
        Pageable pageable = PageRequest.of(0, 10);

        // WHEN
        Page<BuyPriceToQuantityResponseDto> allPriceToQuantityOf = buyRepositoryCustom.findAllPriceToQuantityOf(savedProduct, pageable,
            LocalDateTime.now());

        // THEN
        assertEquals(expensiveBuyBid.getPrice(), allPriceToQuantityOf.getContent().get(0).buyPrice());
        assertEquals(1L, allPriceToQuantityOf.getContent().get(0).quantity());
        assertEquals(samePriceBuyBid.getPrice(),
            allPriceToQuantityOf.getContent().get(allPriceToQuantityOf.getContent().size() - 1).buyPrice());
        assertEquals(2L, allPriceToQuantityOf.getContent().get(allPriceToQuantityOf.getContent().size() - 1).quantity());
    }

    @Test
    @DisplayName("상품에 대한 구매입찰 인스턴스를 조회, 페이징합니다.")
    public void 상품_구매입찰_조회_페이징() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        // WHEN
        Page<Buy> allPricesOf = buyRepositoryCustom.findAllPricesOf(savedProduct, pageable);

        // THEN
        boolean hasBuyBid = allPricesOf.stream()
            .anyMatch(buy -> buy.equals(savedBuy));
        assertTrue(hasBuyBid);
    }

    @Test
    public void 가장첫구매입찰조회() {
        // GIVEN
        int buySaveCount = 10;

        for (int i = 0; i < buySaveCount; i++) {
            buyRepository.save(
                Buy.builder()
                    .price(TEST_BUY_PRICE)
                    .deadlineAt(BuyTest.TEST_DEADLINE_AT)
                    .user(savedBuyer)
                    .product(savedProduct)
                    .build()
            );

        }

        // WHEN
        Buy foundBuy = buyRepositoryCustom.findByProductIdAndPrice(savedProduct.getId(), TEST_BUY_PRICE)
            .orElseThrow(() -> new GlobalException(ResultCase.BUY_BID_NOT_FOUND));

        // THEN
        assertEquals(savedBuy.getCreatedAt(), foundBuy.getCreatedAt());
    }
}