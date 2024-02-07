package bc1.gream.domain.payment.toss.service;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentFailResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.payment.toss.mapper.TossPaymentMapper;
import bc1.gream.domain.payment.toss.repository.TossPaymentRepository;
import bc1.gream.domain.payment.toss.service.event.TossPaymentSuccessCallback;
import bc1.gream.domain.payment.toss.service.event.TossPaymentSuccessEvent;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentRepository tossPaymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${payment.toss.test_client_api_key}")
    private String testClientApiKey;
    @Value("${payment.toss.test_secret_api_key}")
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
     * @param callback   TOSS 최종 요청 결과값 콜백
     */
    @Transactional
    public void requestFinalTossPayment(String paymentKey, String orderId, Long amount, TossPaymentSuccessCallback callback) {
        TossPayment tossPayment = this.verifyRequest(paymentKey, orderId, amount);
        tossPayment.getUser().increasePoint(amount);
        eventPublisher.publishEvent(new TossPaymentSuccessEvent(
            this,
            tossPayment,
            testSecretApiKey,
            callback)); // Provide method reference to the event listener
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
    public TossPaymentFailResponseDto requestFail(String errorCode, String errorMsg, String orderId) {
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
    TossPayment verifyRequest(String paymentKey, String orderId, Long amount) {
        // 주문아이디 일치 검증
        TossPayment tossPayment = this.findBy(orderId);
        // 결제금액 일치 검증
        if (tossPayment.getAmount().equals(amount)) {
            tossPayment.setIsPaySuccess(true);
            tossPayment.setPaymentKey(paymentKey);
            return tossPayment;
        }
        throw new GlobalException(ResultCase.UNMATCHED_PAYMENT_AMOUNT);
    }

    @Transactional(readOnly = true)
    TossPayment findBy(String orderId) {
        return tossPaymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new GlobalException(ResultCase.PAYMENT_NOT_FOUND));
    }
}
