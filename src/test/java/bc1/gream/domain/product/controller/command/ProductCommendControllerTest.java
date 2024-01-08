package bc1.gream.domain.product.controller.command;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.product.service.command.ProductCommendService;
import bc1.gream.test.BaseMvcTest;
import bc1.gream.test.ProductTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = ProductCommendController.class)
class ProductCommendControllerTest extends BaseMvcTest implements ProductTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductCommendService productCommendService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    @Test
//    @WithMockCustomUser(loginId = TEST_USER_LOGIN_ID, password = TEST_USER_PASSWORD)
    public void 사용자_관심상품_추가() throws Exception {
        // WHEN
        productCommendService.likeProduct(TEST_USER, TEST_PRODUCT_ID);

        // THEN
        this.mockMvc.perform(post("/api/products/" + TEST_PRODUCT_ID + "/like")
                .principal(mockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 사용자_관심상품_삭제() throws Exception {
        // WHEN
        productCommendService.likeProduct(TEST_USER, TEST_PRODUCT_ID);

        // THEN
        this.mockMvc.perform(delete("/api/products/" + TEST_PRODUCT_ID + "/dislike"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}