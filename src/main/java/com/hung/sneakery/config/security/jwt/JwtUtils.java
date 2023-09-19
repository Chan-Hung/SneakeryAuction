package com.hung.sneakery.config.security.jwt;

import com.hung.sneakery.config.security.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${hung.com.jwtSecret}")
    private String jwtSecret;

    @Value("${hung.com.jwtExprirationMs}")
    private int jwtExpirationsMs;


    //region Use Cookie - getJwtFromCookie()
    //Get JWT from Cookies by Cookie name
    //and then return cookie's value if cookie is non-null
//    public String getJwtFromCookies(HttpServletRequest request) {
//        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
//        if (cookie != null) {
//            return cookie.getValue();
//        } else {
//            return null;
//        }
//    }
    //endregion

    //region Use Cookie - 1.generateTokenFromUsername() (region with Ctrl + Alt + T)
    //    public String generateTokenFromUsername(String username){
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
    //endregion

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //region Use Cookie - 2.generateJwtCookie()
    //Generating a JwtCookie after signing in, containing username, date, expiration, secret
//    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
//        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
//        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
//                .path("/api")
//                .maxAge(24*60*60) //Expire in 1 day
//                .httpOnly(true)
//                .build();
//        return cookie;
//    }
    //endregion

    //region Use Cookie - getCleanJwtCookie()
    //Cleaning the cookie after logging out
    //public ResponseCookie getCleanJwtCookie(){
    //ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
    //.path("/api")
    //.build();
//        return cookie;
//    }
    //endregion


    public String getEmailFromJwtToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //Validate Jwt Token to verify its authenticity
    //Assign signingKey secret to Jwt
    //If the signature is incorrect, the call to parseClaimsJws() will throw a SignatureException.
    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser()
                    .setSigningKey(jwtSecret) //assign signingKey secret
                    .parseClaimsJws(authToken); //throw 5 exceptions
            return true;
        }catch(SignatureException e){
            logger.error("Invalid JWT signature: {}", e.getMessage());
        }//a JWT was not correctly constructed and be rejected
        catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
