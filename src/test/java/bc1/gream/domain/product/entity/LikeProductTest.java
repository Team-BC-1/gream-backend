package bc1.gream.domain.product.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.product.repository.LikeProductRepository;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.product.repository.ProductRepositoryCustomImpl;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({LikeProduct.class, Product.class, QueryDslConfig.class, ProductRepositoryCustomImpl.class, User.class})
class LikeProductTest implements UserTest, ProductTest {

    @Autowired
    LikeProductRepository likeProductRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(TEST_USER);
        productRepository.save(TEST_PRODUCT);
    }

    @AfterEach
    void tearDown() {
        likeProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    public void 중간조인테이블_복합키_확인() {
        // GIVEN
        LikeProduct likeProduct = LikeProduct.builder().user(TEST_USER).product(TEST_PRODUCT).build();

        // WHEN
        LikeProduct savedLikeProduct = likeProductRepository.save(likeProduct);

        // THEN
        assertEquals(TEST_PRODUCT, savedLikeProduct.getProduct());
        assertEquals(TEST_USER, savedLikeProduct.getUser());
    }
}