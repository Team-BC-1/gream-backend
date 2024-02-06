package bc1.gream.domain.payment.toss.controller;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentFailResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.payment.toss.mapper.TossPaymentMapper;
import bc1.gream.domain.payment.toss.service.PaymentService;
import bc1.gream.domain.payment.toss.validator.TossPaymentRequestValidator;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments/toss")
@RequiredArgsConstructor
public class TossPaymentController {

    private final PaymentService paymentService;

    @PostMapping("/request")
    @Operation(summary = "토스페이 결제 검증/확인 요청", description = "결제정보에 대한 검증/확인 이후 필요한 값들을 반환합니다.")
    public RestResponse<TossPaymentInitialResponseDto> requestTossPayment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody TossPaymentInitialRequestDto requestDto
    ) {
        TossPaymentRequestValidator.validate(requestDto);
        TossPayment payment = TossPaymentMapper.INSTANCE.fromTossPaymentInitialRequestDto(userDetails.getUser(), requestDto);
        TossPaymentInitialResponseDto responseDto = paymentService.requestTossPayment(payment);
        return RestResponse.success(responseDto);
    }

    @GetMapping("/success")
    @Operation(summary = "토스페이 결제 성공 리다이렉트", description = "결제 성공 시 최종 결제 승인 요청을 보냅니다.")
    public RestResponse<TossPaymentSuccessResponseDto> requestFinalTossPayment(
        @Schema(description = "토스 결제고유번호") @RequestParam String paymentKey,
        @Schema(description = "서버 주분고유번호") @RequestParam String orderId,
        @Schema(description = "결제금액") @RequestParam Long amount
    ) throws InterruptedException {
        AtomicReference<TossPaymentSuccessResponseDto> responseDtoHolder = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);

        paymentService.requestFinalTossPayment(paymentKey, orderId, amount, responseDto -> {
            responseDtoHolder.set(responseDto);
            semaphore.release();
        });

        semaphore.acquire();

        return RestResponse.success(responseDtoHolder.get());
    }

    @GetMapping("/fail")
    @Operation(summary = "토스페이 결제 실패 리다이렉트", description = "결제 실패 시 에러코드 및 에러메세지를 반환합니다.")
    public RestResponse<TossPaymentFailResponseDto> requestFail(
        @Schema(description = "에러 코드") @RequestParam String errorCode,
        @Schema(description = "에러 메세지") @RequestParam String errorMsg,
        @Schema(description = "서버 주문고유번호") @RequestParam String orderId
    ) {
        TossPaymentFailResponseDto responseDto = paymentService.requestFail(errorCode, errorMsg, orderId);
        return RestResponse.success(responseDto);
    }
}
