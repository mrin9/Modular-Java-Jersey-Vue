package com.app.util;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.model.user.User;

public class TokenUtil {

    private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

    private static final long VALIDITY_TIME_MS =  2 * 60 * 60 * 1000; // 2 hours  validity
    private static String secret="mrin";


    public static String createTokenForUser(User user)  {
        if (user == null) {
            throw new NullPointerException("username or roles is illegal");
        }
        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
            .setSubject(user.getEmail())
            .claim("id"    , user.getUserId())
            .claim("role"  , user.getRole())
            .claim("fname" , user.getFirstName())
            .claim("lname" , user.getLastName())
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public static User getUserFromToken(String strToken) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(strToken).getBody();
            String id        = (String)claims.get("id");
            String role      = (String)claims.get("role");
            String firstName = (String)claims.get("fname");
            String lastName  = (String)claims.get("lname");
            return new User(id, role, firstName, lastName);
        }
        catch (ExpiredJwtException e){
            log.error("Token Expired");
            return null;
        }
        catch (ClaimJwtException e){
            log.error("Invalid Token");
            return null;
        }

    }


    public static boolean isExpiringIn30Minutes(String strToken) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(strToken).getBody();
        long remainingMinutes =ChronoUnit.MINUTES.between( Instant.now() , claims.getExpiration().toInstant() );
        return (remainingMinutes < 30)?true:false;
    }



}
