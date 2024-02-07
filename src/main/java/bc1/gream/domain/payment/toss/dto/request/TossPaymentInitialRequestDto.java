package bc1.gream.domain.payment.toss.dto.request;

import bc1.gream.domain.payment.toss.entity.OrderName;
import lombok.Builder;

@Builder
public record TossPaymentInitialRequestDto(
    Long amount,        // 결제금액
    OrderName orderName // 주문명
) {

}
