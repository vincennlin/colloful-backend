package com.vincennlin.collofulbackend.config;

import com.vincennlin.collofulbackend.filter.user.AuthenticationFilter;
import com.vincennlin.collofulbackend.filter.user.AuthorizationFilter;
import com.vincennlin.collofulbackend.service.user.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurity {

    private Environment environment;

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        String apiGatewayIp = environment.getProperty("api-gateway.ip");
//
//        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
////        config.setAllowedOrigins(Collections.singletonList("http://" + apiGatewayIp + ":8765"));
////        config.setAllowedOrigins(Collections.singletonList("*"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(Collections.singletonList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//    @Bean
//    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
//        return new CorsFilter(corsConfigurationSource);
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http,
//                                            CorsConfigurationSource corsConfigurationSource,
                                            AuthenticationManager authenticationManager) throws Exception{


        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(userService, environment, authenticationManager);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(corsCustomizer -> corsCustomizer.disable());
//        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource));

//        String webExpressionString =
//                "hasIpAddress('" + environment.getProperty("gateway.ip1") + "') " +
//                "or hasIpAddress('" + environment.getProperty("gateway.ip2") + "')";

        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).access(
//                                new WebExpressionAuthorizationManager(webExpressionString))
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
        );

        http.addFilter(authenticationFilter)
                .addFilter(new AuthorizationFilter(authenticationManager, environment));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        return http.build();
    }
}
