package bc1.gream.domain.sell.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.user.entity.User;
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

    User user;
    Product product;
    Sell sell;

    @Autowired
    private SellRepositoryCustomImpl sellRepositoryCustom;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GifticonRepository gifticonRepository;
    @Autowired
    private SellRepository sellRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(TEST_USER);
        product = productRepository.save(TEST_PRODUCT);
        Gifticon gifticon = gifticonRepository.save(Gifticon.builder()
            .gifticonUrl(TEST_GIFTICON_URL)
            .order(null)
            .build()
        );
        sell = sellRepository.save(
            Sell.builder()
                .price(TEST_SELL_PRICE)
                .deadlineAt(TEST_DEADLINE_AT)
                .product(product)
                .user(user)
                .gifticon(gifticon)
                .build()
        );
    }

    @Test
    @DisplayName("상품에 대한 판매입찰 인스턴스를 조회, 페이징합니다.")
    public void 상품_판매입찰_조회_페이징() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        // WHEN
        Page<Sell> allPricesOf = sellRepositoryCustom.findAllPricesOf(product, pageable);

        // THEN
        boolean hasSellBid = allPricesOf.stream()
            .anyMatch(s -> s.equals(sell));
        assertTrue(hasSellBid);
    }
}