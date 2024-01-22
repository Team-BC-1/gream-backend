package bc1.gream.domain.payment.toss.dto.request;

import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.domain.payment.toss.entity.PayType;

public record TossPaymentInitialRequestDto(
    PayType payType,    // 결제방법
    Long amount,        // 결제금액
    OrderName orderName // 주문명
) {

}
