package com.logistics.snowapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to customize web-related aspects of the Spring Boot application.
 * This class implements {@link WebMvcConfigurer} and provides configuration for CORS to allow
 * cross-origin requests from web clients.
 * <p>
 * The CORS settings are essential for frontend applications hosted on different origins to interact
 * with the backend services without encountering the same-origin policy restrictions imposed by browsers.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #addCorsMappings(CorsRegistry)} - Configures global CORS settings.</li>
 * </ul>
 *
 * @see WebMvcConfigurer
 * @see CorsRegistry
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures the CORS mappings for the application.
     * This method sets up the allowed origins, HTTP methods, and headers that the server will accept from cross-origin requests.
     * <p>
     * Example usage includes allowing HTTP methods such as GET, POST, PUT, DELETE, and OPTIONS from a specific frontend origin.
     *
     * @param registry the {@link CorsRegistry} to configure CORS settings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
