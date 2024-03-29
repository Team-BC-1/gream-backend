package bc1.gream.domain.order.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.test.ProductTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductValidatorTest implements ProductTest {

    @InjectMocks
    private ProductValidator productValidator;
    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_검증_성공_테스트() {

        // given
        given(productRepository.findById(any(Long.class))).willReturn(Optional.of(TEST_PRODUCT));

        // when
        Product product = productValidator.validateBy(TEST_PRODUCT_ID);

        // then
        assertThat(product.getBrand()).isEqualTo(TEST_PRODUCT.getBrand());
        assertThat(product.getPrice()).isEqualTo(TEST_PRODUCT.getPrice());
        assertThat(product.getName()).isEqualTo(TEST_PRODUCT.getName());
    }

    @Test
    void 상품_검증_실패_테스트() {

        // given
        given(productRepository.findById(any())).willReturn(Optional.empty());

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            productValidator.validateBy(TEST_PRODUCT_ID);
        });
        // then
        assertThat(exception.getResultCase().getCode()).isEqualTo(2001);
        assertThat(exception.getResultCase().getMessage()).isEqualTo("해당 상품은 존재하지 않습니다.");

    }

}