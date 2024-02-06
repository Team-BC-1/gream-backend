package bc1.gream.domain.payment.toss.dto.response;

import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.domain.payment.toss.entity.PayType;
import java.time.LocalDateTime;
import lombok.Builder;

/* 결제요청 시, 필요한 값 */
@Builder
public record TossPaymentInitialResponseDto(
    PayType paymentPayType,            // 결제방법
    Long paymentAmount,                // 결제금액
    String paymentOrderId,               // 주문Id
    OrderName paymentOrderName,        // 주문명
    String userLoginId,                // 구매자 아이디
    String userNickname,               // 구매자 닉네임
    String paymentSuccessUrl,          // 주문 성공 시 콜백 주소
    String paymentFailUrl,             // 주문 실패 시 콜백 주소
    LocalDateTime paymentCreatedAt,           // 결제날짜
    Boolean paymentHasSuccess          // 결제 성공 여부
) {

}
