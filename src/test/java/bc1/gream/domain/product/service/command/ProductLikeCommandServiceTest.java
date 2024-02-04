package bc1.gream.domain.product.service.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.product.entity.LikeProduct;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Rollback(value = true)
@Transactional
class ProductLikeCommandServiceTest implements ProductTest, UserTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductLikeCommandService productLikeCommandService;

    @Test
    @DisplayName("관싱상품을 등록합니다.")
    @Disabled("모든 테스트 Suite 실행시킨 경우 SellRepositoryCustomImplTest 의 setup에서 LikeProduct insert 쿼리문이 수행됨")
    public void 관심상품_등록() {
        // GIVEN
        given(productRepository.findById(TEST_PRODUCT_ID)).willReturn(Optional.of(TEST_PRODUCT));

        // WHEN
        productLikeCommandService.likeProduct(TEST_USER, TEST_PRODUCT_ID);

        // THEN
        boolean hasUserLikeProduct = TEST_USER.getLikeProducts().stream()
            .anyMatch(likeProduct ->
                likeProduct.getProduct().getName().equals(TEST_PRODUCT_NAME));
        assertTrue(hasUserLikeProduct);
    }

    @Test
    @DisplayName("관싱상품을 해제합니다.")
    @Disabled("모든 테스트 Suite 실행시킨 경우 SellRepositoryCustomImplTest 의 setup에서 LikeProduct insert 쿼리문이 수행됨")
    public void 관심상품_해제() {
        // GIVEN
        given(productRepository.findById(TEST_PRODUCT_ID)).willReturn(Optional.of(TEST_PRODUCT));
        User user = User.builder().build();
        LikeProduct likeProduct = LikeProduct.builder().user(user).product(TEST_PRODUCT).build();
        user.getLikeProducts().add(likeProduct);

        // WHEN
        productLikeCommandService.dislikeProduct(user, TEST_PRODUCT_ID);

        // THEN
        boolean hasUserLikeProduct = user.getLikeProducts().stream()
            .anyMatch(lp ->
                lp.getProduct().getName().equals(TEST_PRODUCT_NAME));
        assertFalse(hasUserLikeProduct);
    }
}