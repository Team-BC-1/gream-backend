package bc1.gream.global.oauth;

import bc1.gream.global.common.ErrorResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.common.ResultCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "OAuth login failure handler")
@Component
public class OAuthLoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException {
        log.info("ex message : {}", ex.getMessage());
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        settingResponse(res, RestResponse.error(ResultCase.USER_NOT_FOUND, new ErrorResponseDto()));
    }

    private void settingResponse(HttpServletResponse response, ResponseEntity<RestResponse<ErrorResponseDto>> res) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = objectMapper.writeValueAsString(res);
        response.getWriter().write(result);
    }
}
