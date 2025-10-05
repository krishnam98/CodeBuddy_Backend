package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.Model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    @Value("${JWT_SECRET}")
    private String secretKey;

    private static long ACCESS_TOKEN_EXPIRATION= 1000 * 60 * 15;  // 15 mins
    private static long REFRESH_TOKEN_EXPIRATION= 1000 * 60 * 60 * 24 * 7; // 7 days

//---------- Building Token-------------------
    public String buildToken(Map<String, Object> claims, String subject, long expiration){
        return Jwts.builder()
                .claims(claims)       //map
                .subject(subject)    //username
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getKey())
                .compact();

    }
//---------- Key Management-------------------
    public SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//---------- Generating Tokens-------------------
    public String generateAccessToken(Users user){
        return buildToken(new HashMap<>(), user.getUsername(), ACCESS_TOKEN_EXPIRATION);

    }

    public String generateRefreshToken(Users user){
        return buildToken(new HashMap<>(), user.getUsername(), REFRESH_TOKEN_EXPIRATION);
    }

//---------- Validating Token-------------------
    public boolean validateToken(String token, UserDetails userDetails){
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !istokenExpired(token));
    }

    public boolean istokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

//---------- Claims Extraction-------------------
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
