package bc1.gream.domain.product.service.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.test.ProductTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest implements ProductTest {

    @InjectMocks
    ProductQueryService productQueryService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void 상품단건조회() {
        // GIVEN
        lenient().when(productRepository.save(TEST_PRODUCT)).thenReturn(TEST_PRODUCT);
        lenient().when(productRepository.findById(TEST_PRODUCT_ID)).thenReturn(Optional.of(TEST_PRODUCT));

        // WHEN
        Product product = productQueryService.findBy(TEST_PRODUCT_ID);

        // THEN
        assertEquals(TEST_PRODUCT, product);
    }
}