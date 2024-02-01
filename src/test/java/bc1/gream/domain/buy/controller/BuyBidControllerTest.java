package bc1.gream.domain.buy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.order.validator.ProductValidator;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = BuyBidController.class)
@WithMockCustomUser
@ActiveProfiles("test")
class BuyBidControllerTest implements UserTest, ProductTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    BuyBidProvider buyBidProvider;
    @MockBean
    ProductValidator productValidator;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("구매 입찰 컨트롤러의 기능 중 새로운 구매 입찰을 만드는 기능 성공 테스트")
    void 구매_입찰신청_성공_테스트() throws Exception {

        // given
        BuyBidRequestDto requestDto = BuyBidRequestDto.builder()
            .price(4000L)
            .build();

        String json = objectMapper.writeValueAsString(requestDto);

        BuyBidResponseDto responseDto = BuyBidResponseDto.builder()
            .buyId(1L)
            .price(requestDto.price())
            .build();

        Long productId = 1L;
        given(productValidator.validateBy(any(Long.class))).willReturn(TEST_PRODUCT);
        given(buyBidProvider.buyBidProduct(any(User.class), any(BuyBidRequestDto.class), any(Product.class))).willReturn(responseDto);

        // when - then
        mockMvc.perform(post("/api/buy/{productId}", productId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(0),
                jsonPath("$.message").value("정상 처리 되었습니다"),
                jsonPath("$.data.buyId").value(1L)
            );
    }

    @Test
    void buyCancelBid() {
    }
}