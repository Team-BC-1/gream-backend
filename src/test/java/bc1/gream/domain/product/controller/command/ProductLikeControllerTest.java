package bc1.gream.domain.product.controller.command;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.product.controller.ProductLikeController;
import bc1.gream.domain.product.service.command.ProductLikeService;
import bc1.gream.global.security.WithMockCustomUser;
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

@WebMvcTest(controllers = ProductLikeController.class)
@WithMockCustomUser
class ProductLikeControllerTest extends BaseMvcTest implements ProductTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductLikeService productLikeService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    @Test
    public void 사용자_관심상품_추가() throws Exception {
        // WHEN
        productLikeService.likeProduct(TEST_USER, TEST_PRODUCT_ID);

        // THEN
        this.mockMvc.perform(post("/api/products/" + TEST_PRODUCT_ID + "/like"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 사용자_관심상품_삭제() throws Exception {
        // WHEN
        productLikeService.dislikeProduct(TEST_USER, TEST_PRODUCT_ID);

        // THEN
        this.mockMvc.perform(delete("/api/products/" + TEST_PRODUCT_ID + "/dislike"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}