package bc1.gream.global.swagger;

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
    info = @Info(title = "Gream API DOCS",
        description = "Gream's API could give you a real-time bidding data of .",
        version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String accessToken = "Access-Token";
        String refreshToken = "refresh-Token";

        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList(accessToken)
            .addList(refreshToken);

        SecurityScheme accessTokenScheme = new SecurityScheme()
            .type(Type.APIKEY)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(In.HEADER)
            .name("Access-Token");

        SecurityScheme refreshTokenScheme = new SecurityScheme()
            .type(Type.APIKEY)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(In.HEADER)
            .name("Refresh-Token");

        Components components = new Components()
            .addSecuritySchemes(accessToken, accessTokenScheme)
            .addSecuritySchemes(refreshToken, refreshTokenScheme);

        return new OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}