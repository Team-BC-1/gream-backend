package bc1.gream.domain.product.controller.query;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.test.ProductTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = ProductQueryController.class)
class ProductQueryControllerTest implements ProductTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductQueryService productQueryService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
            new ProductQueryController(productQueryService)
        ).build();
    }

    @Test
    public void 상품_전체조회() throws Exception {
        // GIVEN
        List<Product> products = List.of();
        lenient().when(productQueryService.findAll()).thenReturn(products);

        // WHEN
        // THEN
        this.mockMvc.perform(get("/api/products"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 상품_단건조회() throws Exception {
        // GIVEN
        Long productId = 1L;

        // WHEN
        // THEN
        this.mockMvc.perform(get("/api/products/" + productId))
            .andDo(print())
            .andExpect(status().isOk());
    }
}