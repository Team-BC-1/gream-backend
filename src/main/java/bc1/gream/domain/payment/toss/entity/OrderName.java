package bc1.gream.domain.payment.toss.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum OrderName {
    CHARGE_POINT("포인트 충전");

    OrderName(String orderNameKor) {
    }

    /**
     * 요청 입력에 대한 역직렬화 지원함수
     *
     * @param orderNameRequest 요청입력값
     * @return 요청값에 따른 OrderName
     */
    @JsonCreator
    public static OrderName deserializeRequest(String orderNameRequest) {
        return Stream.of(OrderName.values())
            .filter(orderName -> orderName.toString().equals(orderNameRequest.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
