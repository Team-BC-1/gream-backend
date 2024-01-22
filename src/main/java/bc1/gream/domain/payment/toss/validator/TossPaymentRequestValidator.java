package bc1.gream.domain.payment.toss.validator;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.entity.PayType;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;

public final class TossPaymentRequestValidator {


    public static void validate(TossPaymentInitialRequestDto requestDto) {
        validateAmount(requestDto.amount());
        validateSupportedPayType(requestDto.payType());
    }

    private static void validateSupportedPayType(PayType payType) {
        if (!payType.equals(PayType.CARD)) {
            throw new GlobalException(ResultCase.UNSUPPORTED_PAYTYPE);
        }
    }

    private static void validateAmount(Long amount) {
        if (Long.compare(amount, 0L) <= 0L) {
            throw new GlobalException(ResultCase.INVALID_PAYMENT_AMOUNT);
        }
    }
}
