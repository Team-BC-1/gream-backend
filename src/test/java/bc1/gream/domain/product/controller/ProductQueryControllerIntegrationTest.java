package bc1.gream.domain.product.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.product.dto.response.ProductPreviewResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.test.BaseIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled("통합테스트는 로컬에서만 실행합니다. 실행 시, SECRET KEY 에 대한 IntelliJ 환경변수를 설정해주어야 합니다.")
class ProductQueryControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ProductQueryController productQueryController;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseIntegrationTest();
    }

    @Test
    @DisplayName("(조건에 따른) 상품 전체 목록 조회를 합니다.")
    public void 조건_상품전체_목록조회() {
        // GIVEN
        String brand = "스타벅스";
        String name = "아이스";

        // WHEN
        RestResponse<List<ProductPreviewResponseDto>> response = productQueryController.findAll(brand, name);

        // THEN
        List<ProductPreviewResponseDto> result = response.getData();
        boolean hasIced = result.stream()
            .anyMatch(r -> r.name().contains("아이스"));
        assertTrue(hasIced);
    }
}