package com.logistics.snowapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuration class for security settings in the Spring Boot application.
 * This class sets up the security configurations that govern how the application handles
 * Cross-Origin Resource Sharing (CORS) and session management, and defines the security policies
 * for HTTP requests.
 * <p>
 * The class is annotated with {@link Configuration} and {@link EnableWebSecurity} to indicate that it
 * provides Spring Security configuration. Custom beans defined in this class set up CORS support and
 * configure the HTTP security chain.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #corsConfigurationSource()} - Configures the CORS policy for the application.</li>
 *     <li>{@link #securityFilterChain(HttpSecurity)} - Configures the security filter chain for HTTP requests.</li>
 * </ul>
 *
 * @see CorsConfiguration
 * @see SecurityFilterChain
 * @see HttpSecurity
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Defines a bean that provides CORS configuration.
   * Sets the allowed origins, HTTP methods, and headers for CORS, allowing cross-origin requests
   * from specified sources which is crucial for API functionality when accessed from different domains.
   *
   * @return CorsConfigurationSource a source of CORS configurations for the application.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
        "OPTIONS"));
    configuration.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * Configures the security filter chain that applies to HTTP requests.
   * This method sets up CORS support based on the defined CORS configuration, specifies the entry point for
   * handling authentication errors, disables CSRF for API simplicity, and sets the session management policy
   * to stateless which is suitable for REST APIs where each request should be independent of others.
   *
   * @param http the {@link HttpSecurity} to configure
   * @return SecurityFilterChain the configured security filter chain.
   * @throws Exception if an error occurs during the configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(c -> c.configurationSource(corsConfigurationSource()))
        .exceptionHandling(customizer -> customizer
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((requests) -> requests
            .anyRequest().permitAll());
    return http.build();
  }
}