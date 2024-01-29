package bc1.gream.domain.oauth;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import bc1.gream.domain.user.entity.Provider;
import bc1.gream.global.oauth.attributes.OAuthInfoResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RequestOAuthInfoService {

    private final Map<Provider, OAuthAPIClient> clients;

    public RequestOAuthInfoService(List<OAuthAPIClient> clients) {
        this.clients = clients.stream().collect(toUnmodifiableMap(OAuthAPIClient::oAuthProvider, identity()));
    }

    public OAuthInfoResponse request(OAuthLoginParam params) {
        OAuthAPIClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }
}
