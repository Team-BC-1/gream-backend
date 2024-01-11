package bc1.gream.global.security;

import bc1.gream.domain.user.dto.response.UserLogoutResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.common.ResultCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "logout success handler")
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        SecurityContextHolder.clearContext();

        try {
            String json = createResponseJson(response);
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("logout response to json error", e);
        }
    }

    private String createResponseJson(HttpServletResponse response) throws JsonProcessingException {
        response.setStatus(ResultCase.SUCCESS.getHttpStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ObjectMapper().writeValueAsString(RestResponse.success(new UserLogoutResponseDto()));
    }
}
