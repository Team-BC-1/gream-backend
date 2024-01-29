package bc1.gream.domain.oauth.dto.request;

import bc1.gream.domain.user.entity.Provider;

public record OAuthLoginRequestDto(
    String oauthId,
    String nickname,
    Provider provider
) {

}
