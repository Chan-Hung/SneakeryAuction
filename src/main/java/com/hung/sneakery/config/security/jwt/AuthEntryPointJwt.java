package com.hung.sneakery.config.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


    //Catch exception about unauthorization
    //HttpServletResponse.SC_UNAUTHORIZED is the 401 Status code
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String jsonResponse = String.format("{\"success\": false, \"exceptionType\": \"AuthenticationException\", \"message\": \"%s\"}", authException.getMessage());
        // Write the JSON response
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonResponse);
        }
    }
}
