package bc1.gream.domain.buy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCheckOrderResponseDto;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.gifticon.service.query.GifticonQueryService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.security.WithMockCustomUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = UserBoughtHistoryController.class)
@WithMockCustomUser
@ActiveProfiles("test")
class UserBoughtHistoryControllerTest {

    @MockBean
    GifticonQueryService gifticonQueryService;
    @MockBean
    BuyQueryService buyQueryService;
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
    void 사용자가_구매한_전체_기프티콘_목록_조회_컨트롤러_기능_성공_테스트() throws Exception {

        // given
        List<BuyCheckOrderResponseDto> responseDtoList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            BuyCheckOrderResponseDto responseDto = BuyCheckOrderResponseDto.builder()
                .orderId((long) i)
                .expectedPrice(1000L * i)
                .build();

            responseDtoList.add(responseDto);
        }

        given(gifticonQueryService.getBoughtOrder(any(User.class))).willReturn(responseDtoList);

        // when - then
        mockMvc.perform(get("/api/buy/history/end"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(0),
                jsonPath("$.message").value("정상 처리 되었습니다"),
                jsonPath("$.data.size()").value(5)
            );
    }

    @Test
    void 사용자가_등록한_구매입찰_전체_조회_컨트롤러_기능_성공_테스트() throws Exception {

        // given
        List<BuyCheckBidResponseDto> responseDtoList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            BuyCheckBidResponseDto responseDto = BuyCheckBidResponseDto.builder()
                .buyId((long) i)
                .price(1000L * i)
                .build();

            responseDtoList.add(responseDto);
        }

        given(buyQueryService.findAllBuyBidCoupon(any(User.class))).willReturn(responseDtoList);
        // when - then
        mockMvc.perform(get("/api/buy/history/onprogress"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(0),
                jsonPath("$.message").value("정상 처리 되었습니다"),
                jsonPath("$.data.size()").value(5)
            );
    }
}