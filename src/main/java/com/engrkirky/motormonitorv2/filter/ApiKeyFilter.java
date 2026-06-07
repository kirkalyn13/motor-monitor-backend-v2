package com.engrkirky.motormonitorv2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that validates API keys for incoming requests.
 */
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    @Value("${api.key}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Validates the API key and processes the request if authorized.
     *
     * @param request incoming HTTP request
     * @param response outgoing HTTP response
     * @param filterChain filter chain to continue request processing
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestApiKey = request.getHeader("X-API-Key");

        if (apiKey.equals(requestApiKey)) {
            filterChain.doFilter(request, response);
        } else {
            ErrorMessage errorMessage = new ErrorMessage(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid or missing API Key.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorMessage));
        }
    }

    /**
     * Determines whether the API key filter should be skipped.
     *
     * @param request incoming HTTP request
     * @return true if filtering should be skipped, otherwise false
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs");
    }

    /**
     * Error message response.
     */
    private record ErrorMessage (int status, String message) {}
}
