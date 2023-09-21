package dinosoul.snacktravelserver.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dinosoul.snacktravelserver.global.jwt.JwtAuthenticationFilter;
import dinosoul.snacktravelserver.global.jwt.JwtAuthorizationFilter;
import dinosoul.snacktravelserver.global.jwt.JwtUserDetailsServiceImpl;
import dinosoul.snacktravelserver.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityFilter {

    private final JwtProvider jwtProvider;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final WebConfig webConfig;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider, objectMapper);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        return jwtAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, jwtUserDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(WebSecurityFilter::stateless)
                .authorizeHttpRequests(authorizationRequest -> authorizationRequest
                        .requestMatchers("/auth/login", "/auth/signup").permitAll()
                        .requestMatchers(POST, "/comments").permitAll()
                        .requestMatchers(GET, "/snack").permitAll()
                        .requestMatchers(GET, "/travlespot").permitAll()
                        .requestMatchers(GET, "/foodspot").permitAll()
                        .requestMatchers(GET, "/comment/snack/{snackId}").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()))
                .addFilter(webConfig.corsFilter())
                .addFilterBefore(jwtAuthenticationFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, AuthenticationException) -> response.getWriter().write(AuthenticationException.getMessage());
    }

    private static void stateless(SessionManagementConfigurer<HttpSecurity> sessionManagementConfigurer) {
        sessionManagementConfigurer.sessionCreationPolicy(STATELESS);
    }

}
