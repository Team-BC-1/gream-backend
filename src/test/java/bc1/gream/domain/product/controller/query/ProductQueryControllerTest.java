package bc1.gream.domain.product.controller.query;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.product.controller.ProductQueryController;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.provider.BuyOrderQueryProvider;
import bc1.gream.domain.product.provider.SellOrderQueryProvider;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.domain.sell.provider.ProductOrderQueryProvider;
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
    private ProductQueryService productQueryService;
    @MockBean
    private ProductOrderQueryProvider productOrderQueryProvider;
    @MockBean
    private SellOrderQueryProvider sellOrderQueryProvider;
    @MockBean
    private BuyOrderQueryProvider buyOrderQueryProvider;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new ProductQueryController(
                    productQueryService,
                    productOrderQueryProvider,
                    sellOrderQueryProvider,
                    buyOrderQueryProvider
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
        lenient().when(productQueryService.findAll()).thenReturn(products);

        // WHEN
        // THEN
        this.mockMvc.perform(get("/api/products"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 상품_전체조회_상세조건() throws Exception {
        // GIVEN
        List<Product> products = List.of();
        lenient().when(productQueryService.findAll()).thenReturn(products);

        // WHEN
        // THEN
        this.mockMvc.perform(get("/api/products?brand=스타벅스&name=아이스아메리카노&startPrice=4000&endPrice=10000"))
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