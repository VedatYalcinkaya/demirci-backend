package com.demirciyazilim.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                // Public endpoints
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/blogs/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/blogs/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/blogs/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/blogs/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/blogs/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.GET, "/api/v1/references/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/references/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/references/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/references/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/references/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers(HttpMethod.GET, "/api/v1/content-blocks/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/content-blocks/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/content-blocks/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/content-blocks/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/content-blocks/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                                .requestMatchers("/api/v1/users/**").hasAnyRole("ADMIN", "EDITOR")
                                .requestMatchers("/api/v1/files/**").permitAll()
                                .requestMatchers("/api/v1/contact/**").permitAll()
                                // Protected endpoints
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
} 