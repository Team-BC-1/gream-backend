package bc1.gream.domain.product.controller.query;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.common.facade.BuyOrderQueryFacade;
import bc1.gream.domain.common.facade.ProductOrderQueryFacade;
import bc1.gream.domain.common.facade.SellOrderQueryFacade;
import bc1.gream.domain.product.controller.ProductQueryController;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.test.ProductTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = ProductQueryController.class)
//@EnableSpringDataWebSupport
class ProductQueryControllerTest implements ProductTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductOrderQueryFacade productOrderQueryFacade;
    @MockBean
    private SellOrderQueryFacade sellOrderQueryFacade;
    @MockBean
    private BuyOrderQueryFacade buyOrderQueryFacade;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new ProductQueryController(
                    productService,
                    productOrderQueryFacade,
                    sellOrderQueryFacade,
                    buyOrderQueryFacade
                ))
            .setCustomArgumentResolvers(
                new PageableHandlerMethodArgumentResolver()
            )
            .build();
    }

    @Test
    public void 상품_전체조회() throws Exception {
        // GIVEN
        List<Product> products = List.of();
        lenient().when(productService.findAll()).thenReturn(products);

        // WHEN
        // THEN
        this.mockMvc.perform(get("/api/products"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 체결_거래_내역_조회() throws Exception {
        // WHEN
        // THEN
        this.mockMvc.perform(
                get("/api/products/" + TEST_PRODUCT_ID + "/trade")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 판매_입찰가_조회() throws Exception {
        // WHEN
        // THEN
        this.mockMvc.perform(
                get("/api/products/{TEST_PRODUCT_ID}/sell?page=1&size=10", TEST_PRODUCT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 구매_입찰가_조회() throws Exception {
        // WHEN
        // THEN
        this.mockMvc.perform(
                get("/api/products/{TEST_PRODUCT_ID}/buy?page=1&size=10", TEST_PRODUCT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}