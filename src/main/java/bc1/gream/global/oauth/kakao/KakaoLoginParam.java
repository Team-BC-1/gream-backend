package bc1.gream.global.oauth.kakao;

import bc1.gream.domain.user.entity.Provider;
import bc1.gream.global.oauth.OAuthLoginParam;
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