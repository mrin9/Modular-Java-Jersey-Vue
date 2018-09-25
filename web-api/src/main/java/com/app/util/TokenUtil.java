package com.app.util;

import com.app.model.user.UserViewModel;
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


    public static String createTokenForUser(UserViewModel userView)  {
        if (userView == null) {
            throw new NullPointerException("username or roles is illegal");
        }

        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
            .claim("id"     , userView.getUserId())
            .claim("role"   , userView.getRole())
            .claim("custId" , userView.getCustomerId())
            .claim("empId"  , userView.getEmployeeId())
            .claim("name"   , userView.getFullName())
            .claim("email"  , userView.getEmail())
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public static UserViewModel getUserFromToken(String strToken) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(strToken).getBody();
            return new UserViewModel(
                (String)claims.get("id"),
                (String)claims.get("role"),
                (String)claims.get("name"),
                (String)claims.get("email"),
                (Integer)claims.get("empId"),
                (Integer)claims.get("custId")
            ) ;
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
