package bc1.gream.domain.sell.repository;

import static bc1.gream.domain.product.entity.QProduct.product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.product.dto.response.SellPriceToQuantityResponseDto;
import bc1.gream.domain.sell.entity.QSell;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.test.BaseDataRepositoryTest;
import bc1.gream.test.SellTest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class SellRepositoryCustomImplTest extends BaseDataRepositoryTest implements SellTest {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private SellRepositoryCustomImpl sellRepositoryCustom;
    @Autowired
    private SellRepository sellRepository;

    @BeforeEach
    void setUp() {
        setUpBaseDataRepositoryTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseDataRepositoryTest();
    }

    @Test
    public void 상품_판매입찰_가격수량_조회() {
        // GIVEN
        Gifticon gifticon1 = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(null)
                .build()
        );
        Sell samePriceBid = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(gifticon1)
                .build()
        );
        Gifticon gifticon2 = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(null)
                .build()
        );
        Sell expensiveSellBid = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE + 100L)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(gifticon2)
                .build()
        );

        // WHEN
        Map<Long, Long> map = queryFactory
            .select(QSell.sell.price, QSell.sell.count())
            .from(QSell.sell)
            .where(QSell.sell.product.eq(product))
            .groupBy(QSell.sell.price)
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(QSell.sell.price),
                tuple -> tuple.get(QSell.sell.count())
            ));

        // THEN
        assertEquals(2L, map.get(samePriceBid.getPrice()));
        assertEquals(1L, map.get(expensiveSellBid.getPrice()));
    }

    @Test
    public void 상품_판매입찰_가격수량_조회_페이징_가격오름차순() {
        // GIVEN
        Gifticon gifticon1 = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(null)
                .build()
        );
        Sell samePriceBid = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(gifticon1)
                .build()
        );
        Gifticon gifticon2 = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(null)
                .build()
        );
        Sell expensiveSellBid = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE + 100L)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(gifticon2)
                .build()
        );
        Pageable pageable = PageRequest.of(1, 10);

        // WHEN
        Page<SellPriceToQuantityResponseDto> allPriceToQuantityOf = sellRepositoryCustom.findAllPriceToQuantityOf(savedProduct, pageable);

        // THEN
        assertEquals(samePriceBid.getPrice(), allPriceToQuantityOf.getContent().get(0).sellPrice());
        assertEquals(2L, allPriceToQuantityOf.getContent().get(0).sellQuantity());
        assertEquals(expensiveSellBid.getPrice(),
            allPriceToQuantityOf.getContent().get(allPriceToQuantityOf.getContent().size() - 1).sellPrice());
        assertEquals(1L, allPriceToQuantityOf.getContent().get(allPriceToQuantityOf.getContent().size() - 1).sellQuantity());
    }

    @Test
    @DisplayName("상품에 대한 판매입찰 인스턴스를 조회, 페이징합니다.")
    public void 상품_판매입찰_조회_페이징() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        // WHEN
        Page<Sell> allPricesOf = sellRepositoryCustom.findAllPricesOf(savedProduct, pageable);

        // THEN
        boolean hasSellBid = allPricesOf.stream()
            .anyMatch(s -> s.equals(savedSell));
        assertTrue(hasSellBid);
    }

    @Test
    @DisplayName("상품에 대한 판매입찰 인스턴스를 조회, 페이징 처리 시, 기본 순서는 최근 날짜순서입니다.")
    public void 상품_판매입찰_조회_페이징_기본순서_최근날짜순() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        Gifticon cheaperGifticon = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(null)
                .build()
        );
        Sell cheaperSellBid = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE - 1000L)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(cheaperGifticon)
                .build()
        );
        Gifticon moreExpensiveGifticon = gifticonRepository.save(
            Gifticon.builder()
                .gifticonUrl(TEST_GIFTICON_URL)
                .order(null)
                .build()
        );
        Sell moreExpensiveSellBid = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE + 1000L)
                .deadlineAt(SellTest.TEST_DEADLINE_AT)
                .product(savedProduct)
                .user(savedSeller)
                .gifticon(moreExpensiveGifticon)
                .build()
        );

        // WHEN
        Page<Sell> allPricesOf = sellRepositoryCustom.findAllPricesOf(TEST_PRODUCT, pageable);

        // THEN
        assertEquals(cheaperSellBid, allPricesOf.getContent().get(0));
        assertEquals(moreExpensiveSellBid, allPricesOf.getContent().get(allPricesOf.getContent().size() - 1));
    }
}