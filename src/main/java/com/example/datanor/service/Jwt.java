package com.example.datanor.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import static io.jsonwebtoken.io.Decoders.BASE64;

@Service
public class Jwt {

    public String getBearerToken(Long id, String username) {
        JwtBuilder builder = Jwts.builder()
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(7).toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setIssuer("weatherAPI")
                .signWith(getSigningKey())
                .claim("id", id)
                .claim("username", username);
        return builder.compact();
    }

    public Key getSigningKey() {
        byte[] keyBytes = BASE64.decode("jsonwebtokenforsiiriweatherapisecretformoresecuritythanpreviously");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
