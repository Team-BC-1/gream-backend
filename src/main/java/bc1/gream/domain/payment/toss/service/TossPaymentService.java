package bc1.gream.domain.payment.toss.service;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.payment.toss.mapper.TossPaymentMapper;
import bc1.gream.domain.payment.toss.repository.TossPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TossPaymentService {

    private final TossPaymentRepository tossPaymentRepository;

    @Value("${payment.toss.test_client_api_key}")
    private String testClientApiKey;
    @Value("${payment.toss.test_secrete_api_key}")
    private String testSecreteApiKey;
    @Value("${payment.toss.success_url}")
    private String successUrl;
    @Value("${payment.toss.fail_url}")
    private String failUrl;

    @Transactional
    public TossPaymentInitialResponseDto requestTossPayment(TossPayment tossPayment) {
        TossPayment savedTossPayment = tossPaymentRepository.save(tossPayment);
        return TossPaymentMapper.INSTANCE.fromTossPaymentInitialResponseDto(savedTossPayment, successUrl, failUrl);
    }
}
