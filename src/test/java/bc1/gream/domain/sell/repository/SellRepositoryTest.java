package bc1.gream.domain.sell.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.exception.GlobalException;
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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class, AuditingConfig.class})
class SellRepositoryTest implements SellTest {

    @Autowired
    GifticonRepository gifticonRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private SellRepository sellRepository;
    private Product savedProduct;
    private User savedUser;
    private Sell savedSell;

    @BeforeEach
    void setUp() {
        savedProduct = productRepository.save(TEST_PRODUCT);
        savedUser = userRepository.save(TEST_USER);

        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(TEST_GIFTICON_URL)
            .build();
        Gifticon savedGifticon = gifticonRepository.save(gifticon);
        Sell sell = Sell.builder()
            .price(TEST_SELL_PRICE)
            .deadlineAt(TEST_DEADLINE_AT)
            .product(savedProduct)
            .user(savedUser)
            .gifticon(savedGifticon)
            .build();
        savedSell = sellRepository.save(sell);
    }

    @Test
    @DisplayName("상품과 가격에 대한 가장 최근 Sell 엔티티를 조회합니다.")
    public void 가장최근Sell_상품가격_조건조회() {
        // GIVEN
        int saveCount = 5;
        for (int i = 0; i < saveCount; i++) {
            Gifticon gifticon = gifticonRepository.save(
                Gifticon.builder()
                    .gifticonUrl(TEST_GIFTICON_URL)
                    .build()
            );
            sellRepository.save(
                Sell.builder()
                    .price(TEST_SELL_PRICE)
                    .deadlineAt(TEST_DEADLINE_AT)
                    .product(savedProduct)
                    .user(savedUser)
                    .gifticon(gifticon)
                    .build()
            );
        }

        // WHEN
        Sell foundSell = sellRepository.findByProductIdAndPrice(savedProduct.getId(), TEST_SELL_PRICE)
            .orElseThrow(() -> new GlobalException(ResultCase.SELL_BID_PRODUCT_NOT_FOUND));

        // THEN
        assertEquals(savedSell.getCreatedAt(), foundSell.getCreatedAt());
    }
}