package bc1.gream.global.oauth.service;

import bc1.gream.domain.oauth.OAuthAttributes;
import bc1.gream.domain.oauth.dto.request.OAuthLoginRequestDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.entity.UserRole;
import bc1.gream.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j(topic = "OAuth2 service")
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("실행은 되니??");
        String redirectUri = userRequest.getClientRegistration().getRedirectUri();
        log.info("redirectUri : {}", redirectUri);
        String authorizationUri = userRequest.getClientRegistration().getProviderDetails().getAuthorizationUri();
        log.info("authorizationUri : {}", authorizationUri);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2 user name : {}", oAuth2User.getName());

        String providerType = userRequest
            .getClientRegistration()
            .getRegistrationId();
        log.info("providerType : {}", providerType);

        String userNameAttributeName = userRequest
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();
        log.info("userNameAttributeName : {}", userNameAttributeName);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthLoginRequestDto req = OAuthAttributes.extract(providerType, attributes);

        User saveUser = userRepository.findByOauthId(req.oauthId())
            .orElseGet(() -> saveNewUser(req));

        Map<String, Object> customAttribute = Map.of(
            userNameAttributeName, attributes.get(userNameAttributeName),
            "loginId", saveUser.getLoginId(),
            "provider", req.provider(),
            "oAuthId", req.oauthId()
        );

        log.info("custom attributes : {}", customAttribute);

        return new DefaultOAuth2User(List.of(UserRole.USER::getAuthority), customAttribute, userNameAttributeName);
    }

    private User saveNewUser(OAuthLoginRequestDto req) {
        return userRepository.save(User.builder()
            .role(UserRole.USER)
            .oauthId(req.oauthId())
            .nickname(req.nickname())
            .provider(req.provider())
            .loginId(UUID.randomUUID().toString().replace("-", ""))
            .password(UUID.randomUUID().toString().replace("-", ""))
            .build());
    }
}
