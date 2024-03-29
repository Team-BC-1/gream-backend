package bc1.gream.domain.payment.toss.repository;

import bc1.gream.domain.payment.toss.entity.TossPayment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {

    Optional<TossPayment> findByOrderId(String orderId);
}