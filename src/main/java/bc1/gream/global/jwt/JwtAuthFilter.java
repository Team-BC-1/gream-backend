package bc1.gream.global.jwt;

import static bc1.gream.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static bc1.gream.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.redis.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 헤더에서 JWT 가져와서 인증 객체 생성
 */
@Slf4j(topic = "JWT authorization")
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final List<RequestMatcher> whiteList =
        List.of(new AntPathRequestMatcher("/api/v1/users/signup", HttpMethod.POST.name()));
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String accessToken = jwtUtil.getTokenWithoutBearer(request.getHeader(ACCESS_TOKEN_HEADER));
        log.info("accessToken : {}", accessToken);

        // access token 비어있으면 인증 미처리
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // access token 으로 유효성 검증
        JwtStatus accessTokenStatus = jwtUtil.validateToken(accessToken);

        switch (accessTokenStatus) {
            case DENIED -> throw new GlobalException(ResultCase.INVALID_TOKEN);
            case ACCESS -> authenticateLoginUser(accessToken);
            case EXPIRED -> authenticateWithRefreshToken(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateLoginUser(String accessToken) {
        // 로그아웃 처리된 경우 검증
        if (!redisUtil.hasKey(jwtUtil.getLoginIdFromToken(accessToken))) {
            throw new GlobalException(ResultCase.LOGIN_REQUIRED);
        }
        // 유효한 토큰이면 인증 처리 후 필터 통과
        setAuthentication(accessToken);
    }

    private void authenticateWithRefreshToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = jwtUtil.getTokenWithoutBearer(request.getHeader(REFRESH_TOKEN_HEADER));
        log.info("refreshToken : {}", refreshToken);

        // Refresh token 가 없다면 필요하다고 알림
        if (refreshToken == null) {
            throw new GlobalException(ResultCase.EXPIRED_ACCESS_TOKEN);
        }

        JwtStatus refreshTokenStatus = jwtUtil.validateToken(refreshToken);

        switch (refreshTokenStatus) {
            case DENIED -> throw new GlobalException(ResultCase.INVALID_TOKEN);
            case EXPIRED -> throw new GlobalException(ResultCase.LOGIN_REQUIRED);
            case ACCESS -> setAuthWithRenewAccessToken(response, refreshToken);
        }
    }

    /**
     * 재발급한 Access token 을 헤더에 추가 후 인증 처리
     */
    private void setAuthWithRenewAccessToken(HttpServletResponse response, String refreshToken) {
        // 응답 헤더에 재발급한 access token 추가
        response.addHeader(ACCESS_TOKEN_HEADER, renewAccessToken(refreshToken));
        setAuthentication(refreshToken);
    }

    /**
     * Refresh token 을 이용하여 Access Token 재발급
     */
    private String renewAccessToken(String refreshToken) {
        log.info("access token 재발급");
        String loginId = jwtUtil.getLoginIdFromToken(refreshToken);
        String role = jwtUtil.getRoleFromToken(refreshToken);
        return jwtUtil.createAccessToken(loginId, role);
    }

    /**
     * 인증 처리
     */
    private void setAuthentication(String token) {
        log.info("인증 처리");
        String loginId = jwtUtil.getLoginIdFromToken(token);
        SecurityContextHolder.getContext().setAuthentication(createAuthentication(loginId));
    }

    /**
     * 인증 객체 생성
     */
    private Authentication createAuthentication(String loginId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * shouldNotFilter 는 true 를 반환하면 필터링 통과시키는 메서드.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 현재 URL 이 화이트 리스트에 존재하는지 체크
        return whiteList.stream().anyMatch(whitePath -> whitePath.matches(request));
    }
}
