package com.engrkirky.motormonitorv2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String[] LOCALHOST_URLS = {
            "https://motor-monitor-frontend.vercel.app",
            "http://localhost:8080",
            "motor-monitor-arduino/1.0"
    };

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v2/**")
                        .allowedOrigins(LOCALHOST_URLS)
                        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
                        .maxAge(3600);
            }
        };
    }
}
