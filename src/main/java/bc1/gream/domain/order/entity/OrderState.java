package bc1.gream.domain.order.entity;

public enum OrderState {
    ORDER_RECEIVED("주문 접수"),
    PAYMENT_CONFIRMED("결제 확인"),
    PREPARING("상품 준비"),
    SHIPPED("상품 발송"),
    IN_TRANSIT("배송 중"),
    DELIVERED("배송 완료"),
    RETURN_REQUESTED("반품 신청"),
    RETURN_ACCEPTED("반품 접수"),
    REFUND_PROCESSED("환불 처리"),
    ORDER_COMPLETED("주문 완료");

    OrderState(String stateKor) {
    }
}
