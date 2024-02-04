package bc1.gream.domain.payment.toss.service.event;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;

@FunctionalInterface
public interface TossPaymentSuccessCallback {

    void handle(TossPaymentSuccessResponseDto responseDto);
}
