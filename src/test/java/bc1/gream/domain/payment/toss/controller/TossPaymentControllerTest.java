package bc1.gream.domain.payment.toss.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentCardDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentFailResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;
import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.domain.payment.toss.entity.PayType;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.payment.toss.service.TossPaymentService;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.UserTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
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

@WebMvcTest(controllers = TossPaymentController.class)
@ActiveProfiles("test")
class TossPaymentControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TossPaymentService tossPaymentService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    @Test
    @WithMockCustomUser
    @DisplayName("결제정보결제정보를 검증/확인 요청에 대해 검증 이후 필요한 값들을 반환합니다.")
    void 토스페이결제_검증확인요청_성공케이스() throws Exception {
        // GIVEN
        String url = "/api/payments/toss/request";
        TossPaymentInitialRequestDto requestDto = TossPaymentInitialRequestDto.builder()
            .payType(PayType.CARD)
            .amount(1000L)
            .orderName(OrderName.CHARGE_POINT)
            .build();
        TossPaymentInitialResponseDto responseDto = TossPaymentInitialResponseDto.builder()
            .paymentPayType(PayType.CARD)
            .paymentAmount(1000L)
            .paymentOrderName(OrderName.CHARGE_POINT)
            .userLoginId(UserTest.TEST_USER_LOGIN_ID)
            .userNickname(UserTest.TEST_USER_NICKNAME)
            .paymentHasSuccess(true)
            .build();
        given(tossPaymentService.requestTossPayment(any(TossPayment.class)))
            .willReturn(responseDto);

        // WHEN
        // THEN
        mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.paymentPayType").value(String.valueOf(PayType.CARD)))
            .andExpect(jsonPath("$.data.paymentAmount").value(1000L))
            .andExpect(jsonPath("$.data.paymentOrderName").value(String.valueOf(OrderName.CHARGE_POINT)))
            .andExpect(jsonPath("$.data.userLoginId").value(UserTest.TEST_USER_LOGIN_ID))
            .andExpect(jsonPath("$.data.userNickname").value(UserTest.TEST_USER_NICKNAME))
            .andExpect(jsonPath("$.data.paymentHasSuccess").value(true));
    }

    @Test
    @DisplayName("결제 성공 시 최종 결제 승인 요청을 TossAPI 에 보냅니다.")
    void 토스페이_결제성공_리다이렉트() throws Exception {
        // GIVEN
        String url = "/api/payments/toss/success";
        String paymentKey = "alisb2039sdbkn";
        Long orderId = 192819120928L;
        Long amount = 1000L;
        TossPaymentCardDto expectedPaymentCard = TossPaymentCardDto.builder()
            .company("현대")
            .number("433012******1234")
            .installmentPlanMonths("0")
            .isInterestFree(String.valueOf(false))
            .approveNo("00000000")
            .useCardPoint(String.valueOf(false))
            .cardType("신용")
            .ownerType("개인")
            .acquireStatus("READY")
            .build();
        TossPaymentSuccessResponseDto responseDto = TossPaymentSuccessResponseDto.builder()
            .mId("tosspayments")
            .version("1.3")
            .paymentKey("5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6")
            .orderId("192819120928L")
            .orderName(String.valueOf(OrderName.CHARGE_POINT))
            .currency("KRW")
            .method(String.valueOf(PayType.CARD))
            .totalAmount("15000")
            .balanceAmount("15000")
            .suppliedAmount("13636")
            .vat("1364")
            .status("DONE")
            .requestedAt("2021-01-01T10:01:30+09:00")
            .approvedAt("2021-01-01T10:05:40+09:00")
            .useEscrow(String.valueOf(false))
            .cultureExpense(String.valueOf(false))
            .card(expectedPaymentCard)
            .type("NORMAL")
            .build();

        // WHEN
        when(tossPaymentService.requestFinalTossPayment(paymentKey, orderId, amount))
            .thenReturn(responseDto);

        // THEN
        mockMvc.perform(
                get(url)
                    .param("paymentKey", paymentKey)
                    .param("orderId", String.valueOf(orderId))
                    .param("amount", String.valueOf(amount))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.mId").value("tosspayments"))
            .andExpect(jsonPath("$.data.version").value("1.3"))
            .andExpect(jsonPath("$.data.paymentKey").value("5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6"))
            .andExpect(jsonPath("$.data.orderId").value("192819120928L"))
            .andExpect(jsonPath("$.data.orderName").value(String.valueOf(OrderName.CHARGE_POINT)))
            .andExpect(jsonPath("$.data.currency").value("KRW"))
            .andExpect(jsonPath("$.data.method").value(String.valueOf(PayType.CARD)))
            .andExpect(jsonPath("$.data.totalAmount").value("15000"))
            .andExpect(jsonPath("$.data.balanceAmount").value("15000"))
            .andExpect(jsonPath("$.data.suppliedAmount").value("13636"))
            .andExpect(jsonPath("$.data.vat").value("1364"))
            .andExpect(jsonPath("$.data.status").value("DONE"))
            .andExpect(jsonPath("$.data.useEscrow").value(String.valueOf(false)))
            .andExpect(jsonPath("$.data.cultureExpense").value(String.valueOf(false)))
            .andExpect(jsonPath("$.data.type").value("NORMAL"))
            .andExpect(jsonPath("$.data.card").value(
                objectMapper.convertValue(
                    expectedPaymentCard,
                    new TypeReference<LinkedHashMap<String, String>>() {
                    }))
            );
    }

    @Test
    @DisplayName("결제 실패 시 에러코드 및 에러메세지를 반환합니다.")
    void 토스페이_결제실패_리다이렉트() throws Exception {
        // GIVEN
        String url = "/api/payments/toss/fail";
        String errorCode = "에러 코드";
        String errorMsg = "에러 메시지";
        Long orderId = 3L;
        TossPaymentFailResponseDto responseDto = TossPaymentFailResponseDto.builder()
            .errorCode(errorCode)
            .errorMsg(errorMsg)
            .orderId(orderId)
            .build();

        // WHEN
        when(tossPaymentService.requestFail(errorCode, errorMsg, orderId))
            .thenReturn(responseDto);

        // THEN
        mockMvc.perform(
                get(url)
                    .param("errorCode", errorCode)
                    .param("errorMsg", errorMsg)
                    .param("orderId", String.valueOf(orderId))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.errorCode").value(errorCode))
            .andExpect(jsonPath("$.data.errorMsg").value(errorMsg))
            .andExpect(jsonPath("$.data.orderId").value(String.valueOf(orderId)));
    }
}