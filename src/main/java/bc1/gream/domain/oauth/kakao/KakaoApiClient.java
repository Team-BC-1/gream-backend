package bc1.gream.domain.oauth.kakao;

import bc1.gream.domain.oauth.OAuthAPIClient;
import bc1.gream.domain.oauth.OAuthLoginParam;
import bc1.gream.domain.user.entity.Provider;
import bc1.gream.global.oauth.attributes.OAuthInfoResponse;
import bc1.gream.global.oauth.attributes.kakao.KakaoInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthAPIClient {

    private static final String GRANT_TYPE = "authorization_code";
    private final RestTemplate restTemplate;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String authUrl;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String apiUrl;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Override
    public Provider oAuthProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParam params) {
//        String url = authUrl + "/oauth/token";
        String url = authUrl;
        log.info("url : {}", url);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", "http://localhost:5173/login/kakao");
        log.info("body : {}", body);
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        KakaoToken response = restTemplate.postForObject(url, request, KakaoToken.class);
//        String response = restTemplate.postForObject(url, request, String.class);
        log.info("res : {}", response);

        assert response != null;
        return response.getAccessToken();
//        return "";
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
//        String url = apiUrl + "/v2/user/me";
        String url = apiUrl;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}