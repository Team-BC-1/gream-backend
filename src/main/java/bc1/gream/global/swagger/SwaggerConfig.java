package bc1.gream.global.swagger;

import bc1.gream.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Gream API DOCS",
        description = "Gream API를 사용하여 실시간 기프티콘 경매 거래를 요청 및 처리할 수 있습니다.",
        version = "v1"
    )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList(JwtUtil.ACCESS_TOKEN_HEADER)
            .addList(JwtUtil.REFRESH_TOKEN_HEADER);

        SecurityScheme accessTokenScheme = new SecurityScheme()
            .type(Type.APIKEY)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(In.HEADER)
            .name(JwtUtil.ACCESS_TOKEN_HEADER);

        SecurityScheme refreshTokenScheme = new SecurityScheme()
            .type(Type.APIKEY)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(In.HEADER)
            .name(JwtUtil.REFRESH_TOKEN_HEADER);

        Components components = new Components()
            .addSecuritySchemes(JwtUtil.ACCESS_TOKEN_HEADER, accessTokenScheme)
            .addSecuritySchemes(JwtUtil.REFRESH_TOKEN_HEADER, refreshTokenScheme);

        return new OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}