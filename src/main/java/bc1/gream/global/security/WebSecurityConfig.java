package bc1.gream.global.security;

import bc1.gream.global.exception.ExceptionHandlerFilter;
import bc1.gream.global.jwt.JwtAuthFilter;
import bc1.gream.global.jwt.JwtLoginFilter;
import bc1.gream.global.jwt.JwtUtil;
import bc1.gream.global.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final LogoutHandler logoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtLoginFilter jwtLoginFilter() throws Exception {
        JwtLoginFilter filter = new JwtLoginFilter(jwtUtil, redisUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() throws Exception {
        return new JwtAuthFilter(jwtUtil, redisUtil, userDetailsService);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        settingRequestAuthorization(http);
        settingFilterOrder(http);
        settingLogout(http);

        return http.build();
    }

    private void settingRequestAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz ->
            authz
                // 정적 파일
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 유저 도메인
                .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll() // 굿
                // 상품 도메인
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                // health 촏차
                .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                // Swagger
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // 그 외
                .anyRequest().authenticated()
        );
    }

    private void settingFilterOrder(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthFilter(), JwtLoginFilter.class);
        http.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter(), LogoutFilter.class);
    }

    private void settingLogout(HttpSecurity http) throws Exception {
        http.logout(
            logout -> {
                logout.logoutUrl("/api/users/logout");
                logout.addLogoutHandler(logoutHandler);
                logout.logoutSuccessHandler(logoutSuccessHandler);
            });
    }
}
