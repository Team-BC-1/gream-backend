package bc1.gream.domain.buy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.jpa.AuditingConfig;
import bc1.gream.test.BuyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class, AuditingConfig.class})
class BuyRepositoryCustomImplTest implements BuyTest {

    @Autowired
    private BuyRepositoryCustomImpl buyRepositoryCustom;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyRepository buyRepository;
    private Buy savedBuy;
    private Product savedProduct;

    @BeforeEach
    void setUp() {
        userRepository.save(TEST_USER);
        savedProduct = productRepository.save(TEST_PRODUCT);
        savedBuy = buyRepository.save(TEST_BUY);
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
                    .deadlineAt(TEST_DEADLINE_AT)
                    .user(TEST_USER)
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