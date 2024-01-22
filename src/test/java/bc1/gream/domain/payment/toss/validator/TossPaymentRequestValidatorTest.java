package bc1.gream.domain.payment.toss.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.domain.payment.toss.entity.PayType;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import org.junit.jupiter.api.Test;

class TossPaymentRequestValidatorTest {

    @Test
    public void 토스페이결제초기요청_검증_해피케이스() {
        // GIVEN
        TossPaymentInitialRequestDto requestDto = new TossPaymentInitialRequestDto(PayType.CARD, 3000L, OrderName.CHARGE_POINT);

        // WHEN
        // THEN
        assertDoesNotThrow(() -> TossPaymentRequestValidator.validate(requestDto));
    }

    @Test
    public void 토스페이결제초기요청_검증_언해피케이스_미지원결제방법() {
        // GIVEN
        TossPaymentInitialRequestDto requestDto = new TossPaymentInitialRequestDto(PayType.GIFT_CARD, 3000L, OrderName.CHARGE_POINT);

        // WHEN
        GlobalException globalException = assertThrows(GlobalException.class,
            () -> TossPaymentRequestValidator.validate(requestDto));

        // THEN
        assertEquals(ResultCase.UNSUPPORTED_PAYTYPE, globalException.getResultCase());
    }

    @Test
    public void 토스페이결제초기요청_검증_언해피케이스_결제금액() {
        // GIVEN
        TossPaymentInitialRequestDto zeroAmountRequest = new TossPaymentInitialRequestDto(PayType.CARD, 0L, OrderName.CHARGE_POINT);
        TossPaymentInitialRequestDto negativeAmountRequest = new TossPaymentInitialRequestDto(PayType.CARD, -1000L, OrderName.CHARGE_POINT);

        // WHEN
        GlobalException zeroAmountException = assertThrows(GlobalException.class,
            () -> TossPaymentRequestValidator.validate(zeroAmountRequest));
        GlobalException negativeAmountException = assertThrows(GlobalException.class,
            () -> TossPaymentRequestValidator.validate(negativeAmountRequest));

        // THEN
        assertEquals(ResultCase.INVALID_PAYMENT_AMOUNT, zeroAmountException.getResultCase());
        assertEquals(ResultCase.INVALID_PAYMENT_AMOUNT, negativeAmountException.getResultCase());
    }
}