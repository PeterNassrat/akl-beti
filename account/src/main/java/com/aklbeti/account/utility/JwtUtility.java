package com.aklbeti.account.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtility {

    private final String password;

    public JwtUtility(@Value("${jwt.password}") String password) {
        System.out.println(password);
        this.password = password;
    }

    public String generateToken(String id) {
        SecretKey key = Keys.hmacShaKeyFor(password.getBytes(StandardCharsets.UTF_8));

        // 1 hour
        long validityDuration = 1000 * 60 * 60;
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validityDuration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}
