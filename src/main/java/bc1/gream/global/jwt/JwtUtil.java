package bc1.gream.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JWt Util")
@Component
public class JwtUtil {

    public static final String ACCESS_TOKEN_HEADER = "Access-Token"; // Access Token Header KEY 값
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token"; // Refresh Token Header KEY 값
    public static final String AUTHORIZATION_KEY = "auth"; // JWT 내의 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer "; // Token 식별자
    public static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // Access token 만료시간 60분
    public static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // Refresh token 만료시간 2주

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    /**
     * AccessToken 생성
     */
    public String createAccessToken(String loginId, String role) {
        Date now = new Date();

        return Jwts.builder()
            .setSubject(loginId) // 사용자 식별자값(ID)
            .claim(AUTHORIZATION_KEY, role) // 사용자 권한
            .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME)) // 만료 시간
            .setIssuedAt(now) // 발급일
            .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘 (HS256)
            .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(String loginId, String role) {
        Date now = new Date();

        return Jwts.builder()
            .setSubject(loginId) // 사용자 식별자값(ID)
            .claim(AUTHORIZATION_KEY, role) // 사용자 권한
            .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME)) // 만료 시간
            .setIssuedAt(now) // 발급일
            .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘 (HS256)
            .compact();
    }

    /**
     * Bearer prefix 없는 토큰 가져오기
     */
    public String getTokenWithoutBearer(String token) {
        if (hasTokenBearerBearer(token)) {
            return token.substring(7);
        }
        return token;
    }

    /**
     * 토큰에 Bearer prefix 추가하기
     */
    public String setTokenWithBearer(String token) {
        if (!hasTokenBearerBearer(token)) {
            return BEARER_PREFIX + token;
        }
        return token;
    }

    private boolean hasTokenBearerBearer(String token) {
        return StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX);
    }

    /**
     * 토큰에서 사용자 정보 가져오기
     */
    public Claims getUserInfoFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * 토큰에서 로그인 id 가져오기
     */
    public String getLoginIdFromToken(String token) {
        return getUserInfoFromToken(token).getSubject();
    }

    /**
     * 토큰에서 role 가져오기
     */
    public String getRoleFromToken(String token) {
        return (String) getUserInfoFromToken(token).get(AUTHORIZATION_KEY);
    }

    /**
     * 토큰 검증
     */
    public JwtStatus validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return JwtStatus.ACCESS;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            return JwtStatus.EXPIRED;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return JwtStatus.DENIED;
    }
}