package com.hung.sneakery.config.security.jwt;

import com.hung.sneakery.config.security.impl.UserDetailsServiceImpl;
import com.hung.sneakery.utils.SneakeryConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(SneakeryConstant.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //get JWT from the HTTP Auth Header
            String jwt = parseJwt(request);
            if (Objects.nonNull(jwt) && jwtUtils.validateJwtToken(jwt)) {

                //extract user information from token
                String email = jwtUtils.getEmailFromJwtToken(jwt);

                //Retrieve email as a username from DB and then return an userDetails object
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                //create Authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Store Authentication object in SecurityContext
                //after that, set the current UserDetails in SecurityContext
                //with method SecurityContextHolder()
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
