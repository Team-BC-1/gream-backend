package bc1.gream.domain.payment.toss.service.event;

import bc1.gream.domain.payment.toss.dto.response.TossPaymentSuccessResponseDto;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

@Component
public class TossPaymentEventListener {

    @TransactionalEventListener
    public void handleTossPaymentSuccess(TossPaymentSuccessEvent event) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String testSecretApiKey = event.getTestSecretApiKey() + ":";
        String encodedAuth = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("orderId", event.getOrderId());
        param.put("amount", event.getAmount());

        TossPaymentSuccessResponseDto responseDto = rest.postForObject(
            event.getSuccessUrl() + event.getPaymentKey(),
            new HttpEntity<>(param, headers),
            TossPaymentSuccessResponseDto.class
        );

        // Use the functional interface to pass the result back to TossPaymentController
        event.getCallback().handle(responseDto);
    }
}
