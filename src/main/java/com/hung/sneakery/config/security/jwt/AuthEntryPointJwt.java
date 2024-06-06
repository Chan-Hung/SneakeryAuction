package com.hung.sneakery.config.security.jwt;

import com.hung.sneakery.utils.SneakeryConstant;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        LOGGER.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(SneakeryConstant.APPLICATION_JSON);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String authenticationExceptionResponse = String.format(SneakeryConstant.AUTHENTICATION_EXCEPTION_RESPONSE, authException.getMessage());
        // Write the JSON response
        try (PrintWriter writer = response.getWriter()) {
            writer.write(authenticationExceptionResponse);
        }
    }
}