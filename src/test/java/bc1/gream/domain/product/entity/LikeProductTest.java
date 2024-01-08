package bc1.gream.domain.product.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.product.repository.LikeProductRepository;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.product.repository.ProductRepositoryCustomImpl;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
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

    @Test
    public void 중간조인테이블_복합키_확인() {
        // GIVEN
        LikeProduct likeProduct = new LikeProduct(TEST_USER, TEST_PRODUCT);

        // WHEN
        LikeProduct savedLikeProduct = likeProductRepository.save(likeProduct);

        // THEN
        assertEquals(TEST_PRODUCT, savedLikeProduct.getProduct());
        assertEquals(TEST_USER, savedLikeProduct.getUser());
    }

    @Test
    public void 사용자_관심상품_추가합니다() {
        // WHEN
        TEST_USER.addLikeProduct(TEST_PRODUCT);

        // THEN
        boolean hasLikeProduct = likeProductRepository.findAll().stream()
            .filter(likeProduct ->
                likeProduct.getProduct().equals(TEST_PRODUCT))
            .anyMatch(likeProduct ->
                likeProduct.getUser().equals(TEST_USER));
        assertTrue(hasLikeProduct);
    }


    @Test
    public void 사용자_관심상품_삭제합니다() {
        // GIVEN
        TEST_USER.addLikeProduct(TEST_PRODUCT);

        // WHEN
        TEST_USER.removeLikeProduct(TEST_PRODUCT);

        // THEN
        boolean hasLikeProduct = likeProductRepository.findAll().stream()
            .filter(lp ->
                lp.getProduct().equals(TEST_PRODUCT))
            .anyMatch(lp ->
                lp.getUser().equals(TEST_USER));
        assertFalse(hasLikeProduct);
    }
}