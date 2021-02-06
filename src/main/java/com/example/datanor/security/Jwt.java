package com.example.datanor.security;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class Jwt {

    public String getBearerToken(Long intranetuserId, String username, boolean isAdmin) {
        JwtBuilder builder = Jwts.builder()
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(7).toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setIssuer("intranet")
                .signWith(SignatureAlgorithm.HS256, "secret")
                .claim("intranetuserId", intranetuserId)
                .claim("username", username)
                .claim("isAdmin", isAdmin);
        String jwt = builder.compact();
        return jwt;
    }
}