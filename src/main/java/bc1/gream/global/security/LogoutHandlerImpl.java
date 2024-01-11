package bc1.gream.global.security;

import static bc1.gream.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.jwt.JwtStatus;
import bc1.gream.global.jwt.JwtUtil;
import bc1.gream.global.redis.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "localLogin logout")
@Component
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String refreshToken = jwtUtil.getTokenWithoutBearer(request.getHeader(REFRESH_TOKEN_HEADER));
        log.info("refresh token : {}", refreshToken);

        validateValidRefreshToken(refreshToken);
        removeLoginIdInRedis(refreshToken);
    }

    private void validateValidRefreshToken(String refreshToken) {
        JwtStatus refreshTokenStatus = jwtUtil.validateToken(refreshToken);

        if (refreshTokenStatus.equals(JwtStatus.DENIED)) {
            throw new GlobalException(ResultCase.INVALID_TOKEN);
        }
    }

    private void removeLoginIdInRedis(String refreshToken) {
        String loginId = jwtUtil.getLoginIdFromToken(refreshToken);

        if (redisUtil.hasKey(loginId)) {
            log.info("delete redis loginId : {}", loginId);
            redisUtil.delete(loginId);
        }
    }
}
