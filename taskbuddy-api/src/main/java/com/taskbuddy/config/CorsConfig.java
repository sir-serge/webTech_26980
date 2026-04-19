package com.taskbuddy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfig — Global Cross-Origin Resource Sharing (CORS) configuration.
 *
 * What is CORS?
 * ─────────────
 * Browsers enforce the "Same-Origin Policy": a web page can only make
 * requests to the SAME domain it was served from.
 *
 * In our project:
 *   ● Vue.js frontend runs on  →  http://localhost:5173
 *   ● Spring Boot API runs on  →  http://localhost:8080
 *
 * These are DIFFERENT origins, so the browser will BLOCK requests from
 * Vue to Spring Boot unless the server explicitly allows it via CORS headers.
 *
 * This configuration tells Spring Boot:
 *   "Allow requests from http://localhost:5173 to reach /api/**"
 *
 * Note: @CrossOrigin on the controller is a per-controller shortcut.
 *       This CorsConfig provides a GLOBAL, centralized alternative that
 *       covers ALL controllers at once — preferred in production projects.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            // Apply this CORS policy to every endpoint under /api/
            .addMapping("/api/**")

            // Allow requests from Vue dev server and production domain
            // In production: replace "*" with your actual frontend URL
            .allowedOrigins("http://localhost:5173", "http://localhost:8080")

            // Allowed HTTP methods (all standard REST verbs)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

            // Allow all request headers (e.g., Content-Type, Authorization)
            .allowedHeaders("*")

            // Allow cookies / credentials to be sent cross-origin (if needed)
            .allowCredentials(false)

            // Cache the pre-flight OPTIONS response for 1 hour (3600 seconds)
            // reduces the number of pre-flight requests the browser must make
            .maxAge(3600);
    }
}
