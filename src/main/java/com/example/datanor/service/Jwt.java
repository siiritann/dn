package com.example.datanor.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class Jwt {

    public String getBearerToken(Long id, String username) {
        JwtBuilder builder = Jwts.builder()
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(7).toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setIssuer("weatherAPI")
                .signWith(SignatureAlgorithm.HS256, "jsonwebtokenforsiiriweatherapisecretformoresecuritythanpreviously")
                .claim("id", id)
                .claim("username", username);
        return builder.compact();
    }
}
