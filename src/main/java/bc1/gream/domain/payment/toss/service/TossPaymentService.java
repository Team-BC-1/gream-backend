package bc1.gream.domain.payment.toss.service;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.payment.toss.mapper.TossPaymentMapper;
import bc1.gream.domain.payment.toss.repository.TossPaymentRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TossPaymentService {

    private final TossPaymentRepository tossPaymentRepository;

    @Value("${payment.toss.test_client_api_key}")
    private String testClientApiKey;
    @Value("${payment.toss.test_secrete_api_key}")
    private String testSecretApiKey;
    @Value("${payment.toss.success_url}")
    private String successUrl;
    @Value("${payment.toss.fail_url}")
    private String failUrl;

    @Transactional
    public TossPaymentInitialResponseDto requestTossPayment(TossPayment tossPayment) {
        TossPayment savedTossPayment = tossPaymentRepository.save(tossPayment);
        return TossPaymentMapper.INSTANCE.fromTossPaymentInitialResponseDto(savedTossPayment, successUrl, failUrl);
    }

    @Transactional
    public TossPaymentSuccessResponseDto requestFinalTossPayment(String paymentKey, Long orderId, Long amount) {
        this.verifyRequest(paymentKey, orderId, amount);
        return this.sendFinalRequestToTossApi(paymentKey, orderId, amount);
    }

    @Transactional
    TossPaymentSuccessResponseDto sendFinalRequestToTossApi(String paymentKey, Long orderId, Long amount) {
        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        testSecretApiKey = testSecretApiKey + ":";
        String encodedAuth = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("orderId", orderId);
        param.put("amount", amount);

        return rest.postForObject(
            successUrl + paymentKey,
            new HttpEntity<>(param, headers),
            TossPaymentSuccessResponseDto.class
        );
    }

    @Transactional
    void verifyRequest(String paymentKey, Long orderId, Long amount) {
        // 주문아이디 일치 검증
        TossPayment tossPayment = tossPaymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new GlobalException(ResultCase.PAYMENT_NOT_FOUND));
        // 결제금액 일치 검증
        if (tossPayment.getAmount().equals(amount)) {
            tossPayment.setPaymentKey(paymentKey);
            return;
        }
        throw new GlobalException(ResultCase.UNMATCHED_PAYMENT_AMOUNT);
    }
}
