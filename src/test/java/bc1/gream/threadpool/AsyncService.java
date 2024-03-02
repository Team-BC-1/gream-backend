package bc1.gream.threadpool;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;
import bc1.gream.domain.payment.toss.service.PaymentService;
import jakarta.persistence.EntityManager;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EntityManager entityManager;

    @Async("tossPaymentsExecutor")
    public void doSomethingAsync() {
        // Prepare test data below :
        // INSERT INTO gream.tb_toss_payment (created_at, modified_at, amount, is_pay_success, order_id, order_name, pay_fail_reason, pay_type, payment_key, user) VALUES ('2024-03-02 20:05:39.000000', '2024-03-02 20:05:40.000000', 2000, true, 'orderId', 'CHARGE_POINT', null, null, 'paymentKey', null)
        String paymentKey = "paymentKey";
        String orderId = "orderId";
        Long amount = 2000L;

        AtomicReference<TossPaymentSuccessResponseDto> responseDtoHolder = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);

        // Simulate some workload
        paymentService.requestFinalTossPayment(paymentKey, orderId, amount, responseDto -> {
            responseDtoHolder.set(responseDto);
            semaphore.release();
        });

        entityManager.flush();
    }
}
