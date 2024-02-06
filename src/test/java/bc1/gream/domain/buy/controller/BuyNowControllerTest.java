package bc1.gream.domain.buy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.buy.provider.BuyNowProvider;
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

@WebMvcTest(controllers = BuyNowController.class)
@WithMockCustomUser
@ActiveProfiles("test")
class BuyNowControllerTest implements UserTest, ProductTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    BuyNowProvider buyNowProvider;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("판매 입찰로 등록된 입찰 중 즉시 구매시 컨트롤러 기능 성공 테스트")
    void 즉시_구매_요청_기능_컨트롤러_성공_테스트() throws Exception {

        // given
        BuyNowRequestDto requestDto = BuyNowRequestDto.builder()
            .price(5000L)
            .couponId(1L)
            .build();

        String json = objectMapper.writeValueAsString(requestDto);

        BuyNowResponseDto responseDto = BuyNowResponseDto.builder()
            .orderId(1L)
            .expectedPrice(5000L)
            .finalPrice(4000L)
            .build();

        given(buyNowProvider.buyNowProduct(any(User.class), any(BuyNowRequestDto.class), any(Long.class))).willReturn(responseDto);

        // when - then
        mockMvc.perform(post("/api/buy/{productId}/now", 1L)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(0),
                jsonPath("$.message").value("정상 처리 되었습니다"),
                jsonPath("$.data.orderId").value(1L)
            );

    }
}