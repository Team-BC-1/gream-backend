package bc1.gream.domain.payment.toss.validator;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;

public final class TossPaymentRequestValidator {


    public static void validate(TossPaymentInitialRequestDto requestDto) {
        validateAmount(requestDto.amount());
        validateOrderName(requestDto.orderName());
    }

    private static void validateOrderName(OrderName orderName) {
        if (!orderName.equals(OrderName.CHARGE_POINT)) {
            throw new GlobalException(ResultCase.UNSUPPORTED_ORDER_NAME);
        }
    }

    private static void validateAmount(Long amount) {
        if (Long.compare(amount, 0L) <= 0L) {
            throw new GlobalException(ResultCase.ZERO_NEGATIVE_PAYMENT_AMOUNT);
        }
        if (amount < 100L) {
            throw new GlobalException(ResultCase.LESS_THAN_HUNDRED_WON_PAYMENT_AMOUNT);
        }
    }
}
