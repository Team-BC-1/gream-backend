package bc1.gream.domain.payment.toss.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum PayType {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    ACCOUNT_TRANSFER("계좌이체"),
    MOBILE("휴대폰"),
    GIFT_CARD("상품권"),
    INTERNATIONAL("해외간편결제");

    PayType(String payTypeKor) {
    }

    /**
     * 요청 입력에 대한 역직렬화 지원함수
     *
     * @param payTypeRequest 요청입력값
     * @return 요청값에 따른 PayType
     */
    @JsonCreator
    public static PayType deserializeRequest(String payTypeRequest) {
        return Stream.of(PayType.values())
            .filter(payType -> payType.toString().equals(payTypeRequest.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
