package bc1.gream.domain.payment.toss.service;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentFailResponseDto;
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

    /**
     * 토스페이 초기 요청 핸들러
     *
     * @param tossPayment 토스페이
     * @return 토스페이 초기요청 결과
     */
    @Transactional
    public TossPaymentInitialResponseDto requestTossPayment(TossPayment tossPayment) {
        TossPayment savedTossPayment = tossPaymentRepository.save(tossPayment);
        return TossPaymentMapper.INSTANCE.toTossPaymentInitialResponseDto(savedTossPayment, successUrl, failUrl);
    }

    /**
     * 토스페이 최종 요청 핸들러
     *
     * @param paymentKey 토스 결제고유번호
     * @param orderId    서버 주문고유번호
     * @param amount     결제액
     * @return 토스페이 최종요청 결과
     */
    @Transactional
    public TossPaymentSuccessResponseDto requestFinalTossPayment(String paymentKey, Long orderId, Long amount) {
        this.verifyRequest(paymentKey, orderId, amount);
        return this.sendFinalRequestToTossApi(paymentKey, orderId, amount);
    }

    /**
     * 토스페이 실패 요청 핸들러
     *
     * @param errorCode 에러 코드
     * @param errorMsg  에러 메세지
     * @param orderId   서버 주문고유번호
     * @return 결제실패정보
     */
    @Transactional
    public TossPaymentFailResponseDto requestFail(String errorCode, String errorMsg, Long orderId) {
        TossPayment tossPayment = this.findBy(orderId);
        tossPayment.setIsPaySuccess(false);
        tossPayment.setPayFailReason(errorMsg);
        return TossPaymentMapper.INSTANCE.toTossPaymentFailResponseDto(errorCode, errorMsg, orderId);
    }

    /**
     * 주문아이디와 결제액에 대한 검증 핸들러
     *
     * @param paymentKey 토스 결제고유번호
     * @param orderId    서버 주문고유번호
     * @param amount     결제액
     */
    @Transactional
    void verifyRequest(String paymentKey, Long orderId, Long amount) {
        // 주문아이디 일치 검증
        TossPayment tossPayment = findBy(orderId);
        // 결제금액 일치 검증
        if (tossPayment.getAmount().equals(amount)) {
            tossPayment.setPaymentKey(paymentKey);
            return;
        }
        throw new GlobalException(ResultCase.UNMATCHED_PAYMENT_AMOUNT);
    }

    /**
     * 토스페이API에 POST요청
     *
     * @param paymentKey 토스 결제고유번호
     * @param orderId    서버 주문고유번호
     * @param amount     결제액
     * @return 토스페이API 요청결과
     */
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

    @Transactional(readOnly = true)
    TossPayment findBy(Long orderId) {
        return tossPaymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new GlobalException(ResultCase.PAYMENT_NOT_FOUND));
    }
}
