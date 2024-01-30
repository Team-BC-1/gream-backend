package bc1.gream.global.oauth.kakao;

import bc1.gream.domain.user.entity.Provider;
import bc1.gream.global.oauth.OAuthAPIClient;
import bc1.gream.global.oauth.OAuthInfoResponse;
import bc1.gream.global.oauth.OAuthLoginParam;
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
    @Value("${oauth2.kakao.token-uri}")
    private String authUrl;
    @Value("${oauth2.kakao.user-info-uri}")
    private String apiUrl;
    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    @Value("${oauth2.kakao.redirect-url}")
    private String redirectUrl;

    @Override
    public Provider oAuthProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParam params) {
        String url = authUrl;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUrl);
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        KakaoToken response = restTemplate.postForObject(url, request, KakaoToken.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}