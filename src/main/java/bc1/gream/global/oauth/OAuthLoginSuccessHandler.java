package bc1.gream.global.oauth;

import bc1.gream.domain.oauth.dto.response.OAuthLoginResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.jwt.JwtUtil;
import bc1.gream.global.redis.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "OAuth login success handler")
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException {
        log.info("auth name : {}", authentication.getName());
        OAuthLoginResponseDto responseDto = addTokensInHeader(authentication, res);
        settingResponse(res, RestResponse.success(responseDto));
    }

    private OAuthLoginResponseDto addTokensInHeader(Authentication authentication, HttpServletResponse response) {

        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
        String loginId = (String) userDetails.getAttributes().get("loginId");

        String role = authentication.getAuthorities().stream().toList().get(0).getAuthority();

        String accessToken = jwtUtil.createAccessToken(loginId, role);
        String refreshToken = jwtUtil.createRefreshToken(loginId, role);

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        redisUtil.set(loginId, refreshToken, JwtUtil.REFRESH_TOKEN_TIME);

        return new OAuthLoginResponseDto();
    }

    private void settingResponse(HttpServletResponse response, RestResponse<?> res) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String jsonString = objectMapper.writeValueAsString(res);
        response.getWriter().write(jsonString);
    }
}
