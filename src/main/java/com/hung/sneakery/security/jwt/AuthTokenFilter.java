package com.hung.sneakery.security.jwt;

import com.hung.sneakery.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    //Call getJwtFromCookie() method to get cookie's value
    private String parseJwt(HttpServletRequest request){
        String jwt = jwtUtils.getJwtFromCookies(request);
        return jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //get JWT from the HTTP cookie
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){

                //extract user information from token
                String username = jwtUtils.getUsernameFromJwtToken(jwt);

                //Retrieve username from DB and then return an userDetails object
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //create Authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Store Authentication object in SecurityContext
                //after that, set the current UserDetails in SecurityContext
                //with method SecurityContextHolder()
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }catch (Exception e){
            logger.error("Cannot set user authentication: {}",e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
