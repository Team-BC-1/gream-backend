package bc1.gream.domain.oauth;

import bc1.gream.domain.oauth.dto.request.OAuthLoginRequestDto;
import bc1.gream.domain.user.entity.Provider;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public enum OAuthAttributes {

    KAKAO {
        @Override
        public OAuthLoginRequestDto of(Map<String, Object> attributes) {
            log.info("여기까진 접근 완");
            Map<String, Object> kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profileAttributes = (Map<String, Object>) kakaoAccountAttributes.get("profile");

            return new OAuthLoginRequestDto(
                attributes.get("id").toString(),
                profileAttributes.get("nickname").toString(),
                Provider.KAKAO);
        }
    };

//    private final String providerName;

    public static OAuthLoginRequestDto extract(String providerName, Map<String, Object> attributes) {
        OAuthAttributes oAuthAttributes = Arrays.stream(values())
            .filter(provider -> provider.name().equals(providerName.toUpperCase()))
            .findAny()
            .orElseThrow(() -> new GlobalException(ResultCase.INVALID_OAUTH_PROVIDER));

        return oAuthAttributes.of(attributes);
    }

    public abstract OAuthLoginRequestDto of(Map<String, Object> attributes);
}
