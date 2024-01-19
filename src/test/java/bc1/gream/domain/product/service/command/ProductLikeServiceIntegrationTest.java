package bc1.gream.domain.product.service.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.LikeProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.BaseIntegrationTest;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Disabled("통합테스트는 로컬에서만 실행합니다. 실행 시, SECRET KEY 에 대한 IntelliJ 환경변수를 설정해주어야 합니다.")
@Transactional
class ProductLikeServiceIntegrationTest extends BaseIntegrationTest implements ProductTest, UserTest {

    @Autowired
    private ProductLikeService productLikeService;
    @Autowired
    private LikeProductRepository likeProductRepository;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseIntegrationTest();
    }

    @Test
    @DisplayName("중복된 관심상품 요청을 검증합니다.")
    public void 중복된_관심상품_요청검증() {
        // GIVEN
        Product product = savedIcedAmericano;
        User user = savedBuyer;

        // WHEN
        productLikeService.likeProduct(user, product.getId());

        // THEN
        boolean hasNotLikedThisProduct = user.getLikeProducts().stream()
            .noneMatch(likeProduct -> likeProduct.getProduct().equals(product));
        assertTrue(hasNotLikedThisProduct);
    }
}