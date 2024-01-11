package bc1.gream.domain.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.product.dto.unit.ProductCondition;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.jpa.AuditingConfig;
import bc1.gream.test.ProductTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
class ProductRepositoryCustomImplTest implements ProductTest {

    @Autowired
    ProductRepositoryCustomImpl productRepositoryCustom;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.save(TEST_PRODUCT);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("상품 검색에 따른 조건조회를 합니다.")
    public void 상품_조건조회() {
        // GIVEN
        ProductCondition condition = ProductCondition.builder()
            .brand(TEST_PRODUCT_BRAND)
            .name(TEST_PRODUCT_NAME)
            .startPrice(TEST_PRODUCT_PRICE - 500L)
            .endPrice(TEST_PRODUCT_PRICE + 500L)
            .build();

        // WHEN
        List<Product> products = productRepositoryCustom.findAllBy(condition);

        // THEN
        boolean hasProductInProducts = products.stream()
            .anyMatch(product -> product.getName().equals(TEST_PRODUCT_NAME));
        assertTrue(hasProductInProducts);
    }

    @Test
    @DisplayName("상품 검색에 따른 조건조회 이후 페이징처리합니다.")
    public void 상품_조건조회_페이징() {
        // GIVEN
        ProductCondition condition = ProductCondition.builder()
            .brand(TEST_PRODUCT_BRAND)
            .name(TEST_PRODUCT_NAME)
            .startPrice(TEST_PRODUCT_PRICE - 500L)
            .endPrice(TEST_PRODUCT_PRICE + 500L)
            .build();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        // WHEN
        Page<Product> productPage = productRepositoryCustom.findAllByPaging(condition, pageable);

        // THEN
        boolean hasProductInProducts = productPage.stream()
            .anyMatch(product -> product.getName().equals(TEST_PRODUCT_NAME));
        assertEquals(10, productPage.getSize());
        assertEquals(0, productPage.getNumber());
        assertTrue(hasProductInProducts);
    }
}