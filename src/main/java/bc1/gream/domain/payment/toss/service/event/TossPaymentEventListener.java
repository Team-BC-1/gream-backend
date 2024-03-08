package bc1.gream.domain.payment.toss.service.event;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

@Component
public class TossPaymentEventListener {

    private static TossPaymentSuccessResponseDto sendFinalRequest(RestTemplate rest, HttpHeaders headers, JSONObject param) {
        return rest.postForObject(
            "https://api.tosspayments.com/v1/payments/confirm",
            new HttpEntity<>(param, headers),
            TossPaymentSuccessResponseDto.class
        );
    }

    private static void checkPaymentStatus(TossPaymentSuccessEvent event, TossPaymentSuccessResponseDto responseDto) {
        assert responseDto != null;
        if (responseDto.status().equals("DONE")) {
            event.getCallback().handle(responseDto);
        } else {
            throw new GlobalException(ResultCase.TOSS_FINAL_REQUEST_FAIL);
        }
    }

    private static JSONObject setTossPaymentFinalRequestJSONObject(TossPaymentSuccessEvent event) {
        JSONObject param = new JSONObject();
        param.put("paymentKey", event.getTossPayment().getPaymentKey());
        param.put("orderId", event.getTossPayment().getOrderId());
        param.put("amount", event.getTossPayment().getAmount());
        return param;
    }

    private static void setTossHeaders(TossPaymentSuccessEvent event, HttpHeaders headers) {
        String testSecretApiKey = event.getTestSecretApiKey() + ":";
        String encodedAuth = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Async("tossPaymentsExecutor")
    @TransactionalEventListener
    public void handleTossPaymentSuccess(TossPaymentSuccessEvent event) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        setTossHeaders(event, headers);

        JSONObject param = setTossPaymentFinalRequestJSONObject(event);

        TossPaymentSuccessResponseDto responseDto = sendFinalRequest(rest, headers, param);

        checkPaymentStatus(event, responseDto);
    }
}
