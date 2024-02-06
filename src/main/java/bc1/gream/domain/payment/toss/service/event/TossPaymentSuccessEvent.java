package bc1.gream.domain.payment.toss.service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TossPaymentSuccessEvent extends ApplicationEvent {

    private final String paymentKey;
    private final String orderId;
    private final Long amount;
    private final String successUrl;
    private final String testSecretApiKey;

    private final TossPaymentSuccessCallback callback;

    public TossPaymentSuccessEvent(Object source, String paymentKey, String orderId, Long amount, String successUrl,
        String testSecretApiKey,
        TossPaymentSuccessCallback callback) {
        super(source);
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.successUrl = successUrl;
        this.testSecretApiKey = testSecretApiKey;
        this.callback = callback;
    }
}
