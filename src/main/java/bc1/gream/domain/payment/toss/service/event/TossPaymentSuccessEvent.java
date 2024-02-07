package bc1.gream.domain.payment.toss.service.event;

import bc1.gream.domain.payment.toss.entity.TossPayment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TossPaymentSuccessEvent extends ApplicationEvent {

    private final TossPayment tossPayment;
    private final String testSecretApiKey;

    private final TossPaymentSuccessCallback callback;

    public TossPaymentSuccessEvent(Object source, TossPayment tossPayment, String testSecretApiKey, TossPaymentSuccessCallback callback) {
        super(source);
        this.tossPayment = tossPayment;
        this.testSecretApiKey = testSecretApiKey;
        this.callback = callback;
    }
}
