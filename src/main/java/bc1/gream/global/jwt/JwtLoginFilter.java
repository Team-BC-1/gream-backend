package bc1.gream.global.jwt;

import static bc1.gream.global.common.ResultCase.USER_NOT_FOUND;

import bc1.gream.domain.user.dto.request.UserLoginRequestDto;
import bc1.gream.domain.user.dto.response.UserLoginResponseDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ErrorResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.redis.RedisUtil;
import bc1.gream.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "login filter")
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void setup() {
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // 요청 JSON 파싱
            UserLoginRequestDto req = objectMapper.readValue(request.getInputStream(), UserLoginRequestDto.class);

            log.info("[login try] loginId : {}, password : {}", req.loginId(), req.password());

            // 인증 처리 로직
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(req.loginId(), req.password(), null));

        } catch (IOException e) {
            throw new GlobalException(ResultCase.SYSTEM_ERROR);
        }
    }

    /**
     * 로그인 성공 시 처리 로직
     */
    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        log.info("success auth loginId : {}", authResult.getName());

        UserLoginResponseDto res = getResponseDtoWithTokensInHeader(authResult, response); // 헤더에서 토큰을 추가한 응답 DTO 생성
        settingResponse(response, RestResponse.success(res)); // 공통 응답 객체를 Response 에 담기
    }

    private UserLoginResponseDto getResponseDtoWithTokensInHeader(Authentication authentication, HttpServletResponse response) {

        // JWT 에 들어갈 loginId 를 userDetails 로부터 가져와서
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        // JWT 토큰을 생성하고,
        String role = getRoleInAuthentication(authentication);
        String accessToken = jwtUtil.createAccessToken(user.getLoginId(), role);
        String refreshToken = jwtUtil.createRefreshToken(user.getLoginId(), role);

        // response 객체의 헤더에 Bearer 접두사를 붙여 넣어준 뒤,
        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, jwtUtil.setTokenWithBearer(accessToken));
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, jwtUtil.setTokenWithBearer(refreshToken));

        // 레디스에 loginId 을 키로, Bearer 없는 refresh token 를 벨류로 리프레쉬 토큰 만료 시간만큼 넣어줌.
        redisUtil.set(user.getLoginId(), jwtUtil.getTokenWithoutBearer(refreshToken), JwtUtil.REFRESH_TOKEN_TIME);

        return new UserLoginResponseDto(
            user.getId(),
            user.getLoginId(),
            user.getNickname(),
            user.getRole().getValue(),
            user.getProvider().name());
    }

    private String getRoleInAuthentication(Authentication authResult) {
        List<? extends GrantedAuthority> list = new ArrayList<>(authResult.getAuthorities());
        return list.get(0).getAuthority();
    }

    /**
     * 로그인 실패 시 처리 로직
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
        throws IOException {

        log.info("login failed message : {}", failed.getMessage());

        // 에러 반환
        response.setStatus(USER_NOT_FOUND.getHttpStatus().value()); // HttpStatus 설정
        settingResponse(response, RestResponse.error(USER_NOT_FOUND, new ErrorResponseDto()).getBody()); // 응답 설정
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
}