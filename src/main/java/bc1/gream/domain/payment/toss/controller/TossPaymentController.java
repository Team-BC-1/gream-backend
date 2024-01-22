package bc1.gream.domain.payment.toss.controller;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.payment.toss.mapper.TossPaymentMapper;
import bc1.gream.domain.payment.toss.service.TossPaymentService;
import bc1.gream.domain.payment.toss.validator.TossPaymentRequestValidator;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class TossPaymentController {

    private final TossPaymentService tossPaymentService;

    @PostMapping("/toss/request")
    @Operation(summary = "토스페이 결제 검증/확인 요청", description = "결제정보에 대한 검증/확인 이후 필요한 값들을 반환합니다.")
    public RestResponse<TossPaymentInitialResponseDto> requestTossPayment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody TossPaymentInitialRequestDto requestDto
    ) {
        TossPaymentRequestValidator.validate(requestDto);
        TossPayment payment = TossPaymentMapper.INSTANCE.fromTossPaymentInitialRequestDto(userDetails.getUser(), requestDto);
        TossPaymentInitialResponseDto responseDto = tossPaymentService.requestTossPayment(payment);
        return RestResponse.success(responseDto);
    }
}
