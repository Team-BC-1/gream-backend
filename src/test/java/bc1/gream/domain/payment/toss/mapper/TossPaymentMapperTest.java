//package bc1.gream.domain.payment.toss.mapper;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
//import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
//import bc1.gream.domain.payment.toss.entity.OrderName;
//import bc1.gream.domain.payment.toss.entity.PayType;
//import bc1.gream.domain.payment.toss.entity.TossPayment;
//import bc1.gream.domain.user.entity.User;
//import bc1.gream.test.UserTest;
//import org.junit.jupiter.api.Test;
//
//class TossPaymentMapperTest implements UserTest {
//
////    @Test
////    public void 사용자_토스페이결제초기요청_토스페이로_변환() {
////        // GIVEN
////        User user = TEST_USER;
////        TossPaymentInitialRequestDto requestDto = new TossPaymentInitialRequestDto(PayType.CARD, 3000L, OrderName.CHARGE_POINT);
////
////        // WHEN
////        TossPayment payment = TossPaymentMapper.INSTANCE.fromTossPaymentInitialRequestDto(user, requestDto);
////
////        // THEN
////        assertEquals(payment.getUser(), user);
////        assertEquals(payment.getAmount(), requestDto.amount());
////        assertEquals(payment.getOrderName(), requestDto.orderName());
////    }
//
////    @Test
////    public void 토스페이_성공URL_실패URL_결제요청결과DTO로_변환() {
////        // GIVEN
////        TossPayment tossPayment = TossPayment.builder()
////            .user(TEST_USER)
////            .payType(PayType.CARD)
////            .amount(1000L)
////            .orderId(1L)
////            .orderName(OrderName.CHARGE_POINT)
////            .build();
////        String successUrl = "successUrl";
////        String failUrl = "failUrl";
////
////        // WHEN
////        TossPaymentInitialResponseDto responseDto = TossPaymentMapper.INSTANCE.toTossPaymentInitialResponseDto(tossPayment, successUrl,
////            failUrl);
////
////        // THEN
////        assertEquals(PayType.CARD, responseDto.paymentPayType());
////        assertEquals(1000L, responseDto.paymentAmount());
////        assertEquals(OrderName.CHARGE_POINT, responseDto.paymentOrderName());
////        assertEquals(TEST_USER_LOGIN_ID, responseDto.userLoginId());
////        assertEquals(TEST_USER_NICKNAME, responseDto.userNickname());
////        assertEquals(successUrl, responseDto.paymentSuccessUrl());
////        assertEquals(failUrl, responseDto.paymentFailUrl());
////        assertTrue(responseDto.paymentHasSuccess());
////    }
//
////    @Test
////    @DisplayName("errorCode,errorMsg,orderId 을 입력받아 TossPaymentFailResponseDto 로 매핑합니다.")
////    public void errorCode_errorMsg_orderId_TO_TossPaymentFailResponseDto() {
////        // GIVEN
////        String errorCode = "에러코드";
////        String errorMsg = "에러메세지";
////        Long orderId = 100L;
////
////        // WHEN
////        TossPaymentFailResponseDto failResponseDto = TossPaymentMapper.INSTANCE.toTossPaymentFailResponseDto(errorCode, errorMsg,
////            orderId);
////
////        // THEN
////        assertEquals(failResponseDto.errorCode(), errorCode);
////        assertEquals(failResponseDto.errorMsg(), errorMsg);
////        assertEquals(failResponseDto.orderId(), orderId);
////    }
//}