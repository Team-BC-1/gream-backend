package bc1.gream.domain.product.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WithMockCustomUser
@ActiveProfiles("test")
@Rollback
class ProductLikeControllerIntegrationTest implements ProductTest, UserTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WebApplicationContext context;
    private MockMvc mockMvc;
    private User user;
    private Product product;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
        user = userRepository.save(TEST_USER);
        product = productRepository.save(TEST_PRODUCT);
    }

    @Test
    @DisplayName("로그인한 회원의 관심상품 등록요청에 대한 핸들링을 합니다.")
    public void 관심상품_등록요청_핸들링() throws Exception {
        // WHEN
        // THEN
        this.mockMvc.perform(
                post("/api/products/{productId}/like", product.getId())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인한 회원의 관심상품 해제요청에 대한 핸들링을 합니다.")
    public void 관심상품_해제요청_핸들링() throws Exception {
        // WHEN
        // THEN
        this.mockMvc.perform(
                delete("/api/products/{productId}/dislike", product.getId())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}