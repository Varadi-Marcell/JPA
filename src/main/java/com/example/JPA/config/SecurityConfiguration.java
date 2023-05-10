package com.example.JPA.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.JPA.model.Permission.ADMIN_DELETE;
import static com.example.JPA.model.Role.ADMIN;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public static final String[] ADMIN_PROTECTED_ENDPOINTS = {
            "/api/v1/ticket/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        RequestMatcher adminRoutesRequestMatcher = createAdminRequestMatcher(ADMIN_PROTECTED_ENDPOINTS, HttpMethod.DELETE);
        RequestMatcher adminRoutesRequestMatcherPost = createAdminRequestMatcher(ADMIN_PROTECTED_ENDPOINTS, HttpMethod.POST);

        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**", "/stomp-endpoint/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/ticket/**")
                .permitAll()

                .requestMatchers("/api/v1/user").hasRole("ADMIN")

                .requestMatchers(adminRoutesRequestMatcher)
                .hasRole("ADMIN")

                .requestMatchers(adminRoutesRequestMatcherPost)
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(false)
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private RequestMatcher createAdminRequestMatcher(String[] adminRoutes, HttpMethod httpMethod) {
        List<RequestMatcher> requestMatchers = new ArrayList<>();
        for (String route : adminRoutes) {
            requestMatchers.add(new AntPathRequestMatcher(route,httpMethod.name()));
        }
        System.out.println(requestMatchers);
        return new OrRequestMatcher(requestMatchers);
    }

}