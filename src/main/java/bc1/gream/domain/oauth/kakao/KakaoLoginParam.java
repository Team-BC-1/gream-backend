package bc1.gream.domain.oauth.kakao;

import bc1.gream.domain.oauth.OAuthLoginParam;
import bc1.gream.domain.user.entity.Provider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record KakaoLoginParam(String authorizationCode) implements OAuthLoginParam {

    public Provider oAuthProvider() {
        return Provider.KAKAO;
    }

    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}