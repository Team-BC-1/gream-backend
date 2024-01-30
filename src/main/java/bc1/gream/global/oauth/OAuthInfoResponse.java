package bc1.gream.global.oauth;

import bc1.gream.domain.user.entity.Provider;

public interface OAuthInfoResponse {

    String getOauthId();

    String getNickname();

    String getProfileImageUrl();

    Provider getOAuthProvider();
}