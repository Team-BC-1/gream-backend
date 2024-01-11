package bc1.gream.domain.sell.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.repository.SellRepository;
import bc1.gream.domain.order.repository.SellRepositoryCustomImpl;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.jpa.AuditingConfig;
import bc1.gream.test.SellTest;
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
class SellRepositoryCustomImplTest implements SellTest {

    @Autowired
    private SellRepositoryCustomImpl sellRepositoryCustom;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellRepository sellRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(TEST_USER);
        productRepository.save(TEST_PRODUCT);
        sellRepository.save(TEST_SELL);
    }

    @Test
    @DisplayName("상품에 대한 판매입찰 인스턴스를 조회, 페이징합니다.")
    public void 상품_판매입찰_조회_페이징() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        // WHEN
        Page<Sell> allPricesOf = sellRepositoryCustom.findAllPricesOf(TEST_PRODUCT, pageable);

        // THEN
        boolean hasSellBid = allPricesOf.stream()
            .anyMatch(buy -> buy.equals(TEST_SELL));
        assertTrue(hasSellBid);
    }
}