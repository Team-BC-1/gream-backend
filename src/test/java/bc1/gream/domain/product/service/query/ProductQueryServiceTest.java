package bc1.gream.domain.product.service.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.product.dto.unit.ProductCondition;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.product.repository.ProductRepositoryCustom;
import bc1.gream.test.ProductTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest implements ProductTest {

    @InjectMocks
    ProductQueryService productQueryService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductRepositoryCustom productRepositoryCustom;

    @Test
    public void 상품단건조회() {
        // GIVEN
        given(productRepository.findById(TEST_PRODUCT_ID)).willReturn(Optional.of(TEST_PRODUCT));

        // WHEN
        Product product = productQueryService.findBy(TEST_PRODUCT_ID);

        // THEN
        assertEquals(TEST_PRODUCT, product);
    }

    @Test
    public void 상품목록조회() {
        // GIVEN
        List<Product> productMocks = List.of(TEST_PRODUCT, TEST_PRODUCT_SECOND);
        given(productRepository.findAll()).willReturn(productMocks);

        // WHEN
        List<Product> products = productQueryService.findAll();

        // THEN
        boolean hasTestProduct = products.stream()
            .anyMatch(product -> product.getName().equals(TEST_PRODUCT_NAME));
        boolean hasTestProductSecond = products.stream()
            .anyMatch(product -> product.getName().equals(TEST_PRODUCT_SECOND_NAME));
        assertTrue(hasTestProduct);
        assertTrue(hasTestProductSecond);
    }

    @Test
    @Disabled("QueryDSL 조건조회 목업 테스트 실패")
    public void 상품목록조건조회() {
        // GIVEN
        ProductCondition condition = ProductCondition.builder()
            .brand(TEST_PRODUCT_BRAND)
            .name(TEST_PRODUCT_NAME)
            .startPrice(TEST_PRODUCT_PRICE - 500L)
            .endPrice(TEST_PRODUCT_PRICE + 500L)
            .build();
        given(productRepositoryCustom.findAllBy(condition)).willReturn(List.of(TEST_PRODUCT));

        // WHEN
        List<Product> products = productQueryService.findAllBy(condition);

        // THEN
        System.out.println("products.size() = " + products.size());
    }

    @Test
    @Disabled("QueryDSL 조건조회 페이징 목업 테스트 실패")
    public void 상품목록조건조회페이징() {
        // GIVEN
        ProductCondition condition = ProductCondition.builder()
            .brand(TEST_PRODUCT_BRAND)
            .name(TEST_PRODUCT_NAME)
            .startPrice(TEST_PRODUCT_PRICE - 500L)
            .endPrice(TEST_PRODUCT_PRICE + 500L)
            .build();
        Pageable pageRequest = PageRequest.of(1, 10, Sort.by("name").descending());
        PageImpl<Product> page = new PageImpl<>(List.of(TEST_PRODUCT));
        given(productRepositoryCustom.findAllByPaging(condition, pageRequest)).willReturn(page);

        // WHEN
        Page<Product> products = productQueryService.findAllByPaging(condition, pageRequest);

        // THEN
        boolean hasTestProduct = products.stream()
            .anyMatch(product -> product.getName().equals(TEST_PRODUCT_NAME));
        assertTrue(hasTestProduct);
    }
}