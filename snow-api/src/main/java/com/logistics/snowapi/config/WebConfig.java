package com.logistics.snowapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://ec2-13-59-185-215.us-east-2.compute.amazonaws.com:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION")
                .allowedHeaders("");
    }
}
