package bc1.gream.domain.oauth;

import bc1.gream.domain.user.entity.Provider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParam {

    Provider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}