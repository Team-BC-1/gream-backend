package bc1.gream.global.oauth;

import bc1.gream.domain.user.dto.response.UserLoginResponseDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.entity.UserRole;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.jwt.JwtUtil;
import bc1.gream.global.oauth.kakao.KakaoLoginParam;
import bc1.gream.global.redis.RedisUtil;
import bc1.gream.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "OAuth2 custom filter")
@RequiredArgsConstructor
public class OAuth2Filter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        String code = request.getParameter("code");
        log.info("code : {}", code);

        KakaoLoginParam params = new KakaoLoginParam(code);
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        User user = getOAuthUser(oAuthInfoResponse);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String role = user.getRole().getAuthority();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            userDetails.getPassword(),
            userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        // JWT 토큰을 생성하고,
        String accessToken = jwtUtil.createAccessToken(user.getLoginId(), role);
        String refreshToken = jwtUtil.createRefreshToken(user.getLoginId(), role);

        // response 객체의 헤더에 Bearer 접두사를 붙여 넣어준 뒤,
        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, jwtUtil.setTokenWithBearer(accessToken));
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, jwtUtil.setTokenWithBearer(refreshToken));

        // 레디스에 loginId 을 키로, Bearer 없는 refresh token 를 벨류로 리프레쉬 토큰 만료 시간만큼 넣어줌.
        redisUtil.set(user.getLoginId(), jwtUtil.getTokenWithoutBearer(refreshToken), JwtUtil.REFRESH_TOKEN_TIME);

        List<Long> likeProductIds = userRepository.findAllLikeProductIdByUser(user);

        UserLoginResponseDto responseDto = new UserLoginResponseDto(
            user.getId(),
            user.getLoginId(),
            user.getNickname(),
            user.getRole().getValue(),
            user.getProvider().name(),
            likeProductIds);

        settingResponse(response, RestResponse.success(responseDto));
    }


    private User getOAuthUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByOauthId(oAuthInfoResponse.getOauthId())
            .orElseGet(() -> getNewUser(oAuthInfoResponse));
    }

    private User getNewUser(OAuthInfoResponse oAuthInfoResponse) {
        User saveUser = User.builder()
            .provider(oAuthInfoResponse.getOAuthProvider())
            .nickname(oAuthInfoResponse.getNickname())
            .role(UserRole.USER)
            .oauthId(oAuthInfoResponse.getOauthId())
            .loginId(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .build();

        return userRepository.save(saveUser);
    }

    /**
     * 응답에 JSON 으로 설정하는 로직
     */
    private void settingResponse(HttpServletResponse response, RestResponse<?> res)
        throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // JSON 설정
        response.setCharacterEncoding(StandardCharsets.UTF_8.name()); // UTF8 설정하여 한글 표시

        String result = objectMapper.writeValueAsString(res); // JSON to String 변환
        response.getWriter().write(result);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        AntPathRequestMatcher kakaoRequestMatcher = new AntPathRequestMatcher("/api/users/oauth/kakao", HttpMethod.GET.name());
        return !kakaoRequestMatcher.matches(request);
    }
}
