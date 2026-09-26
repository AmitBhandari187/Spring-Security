package com.spring_security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private String jwtSecret="MySuperSecretKeyForJWTAuthentication2026SpringBoot";
    private int jwtExpirationMs=3600000;

//    1st Step is to generate the token
    public String generateTokenFromUsername(String userName){
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+jwtExpirationMs))
                .signWith(key())
                .compact();
    }

//    2nd step is to retrieve the token
    public String getJwtFromHeader(){
        return "";
    }


//    3rd step is to validate the received token
    public boolean validateJwtToken(){
        return true;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
