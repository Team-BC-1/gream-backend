package bc1.gream.domain.buy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.provider.BuyBidProvider;
import bc1.gream.domain.order.validator.ProductValidator;
import bc1.gream.global.security.UserDetailsImpl;
import bc1.gream.global.security.WebSecurityConfig;
import bc1.gream.test.MockSpringSecurityFilter;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {BuyBidController.class}, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)})
@ActiveProfiles("test")
class BuyBidControllerTest implements UserTest, ProductTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    BuyBidProvider buyBidProvider;
    @MockBean
    ProductValidator productValidator;
    private MockMvc mockMvc;
    private Principal mockPrincipal;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter())).alwaysDo(print()).build();
    }

    private void mockUserSetup() {
        UserDetailsImpl testUserDetails = new UserDetailsImpl(TEST_BUYER);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    void 구매_입찰_컨트롤러_성공_테스트() throws Exception {
        mockUserSetup();

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
        given(buyBidProvider.buyBidProduct(TEST_BUYER, requestDto, TEST_PRODUCT)).willReturn(responseDto);

        mockMvc.perform(post("/api/buy/{productId}", productId)
                .principal(mockPrincipal)
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