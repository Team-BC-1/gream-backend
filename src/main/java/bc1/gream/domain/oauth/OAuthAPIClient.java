package bc1.gream.domain.oauth;

import bc1.gream.domain.user.entity.Provider;
import bc1.gream.global.oauth.attributes.OAuthInfoResponse;

public interface OAuthAPIClient {

    Provider oAuthProvider();

    String requestAccessToken(OAuthLoginParam params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}
