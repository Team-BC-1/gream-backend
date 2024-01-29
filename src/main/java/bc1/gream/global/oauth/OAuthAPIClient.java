package bc1.gream.global.oauth;

import bc1.gream.domain.user.entity.Provider;

public interface OAuthAPIClient {

    Provider oAuthProvider();

    String requestAccessToken(OAuthLoginParam params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}
