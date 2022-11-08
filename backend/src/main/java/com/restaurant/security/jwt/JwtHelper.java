package com.restaurant.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.Map;

@Component
public class JwtHelper {

    @Value("${restaurant.jwt.jwtExpiration}")
    private Integer jwtExpiration;

    private final Key key;

    // extract the key from spring properties file, hash cha key
    public JwtHelper(@Value("${restaurant.jwt.jwtSecret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // create the token with subject and claims
    public String createToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    // parse token for Jws claims
    public Map<String, Object> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
