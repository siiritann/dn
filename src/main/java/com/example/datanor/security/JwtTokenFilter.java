package com.example.datanor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);
        if (token != null) {
            Authentication authentication = validateToken(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    String resolveToken(HttpServletRequest req) {
        String headerAttribute = req.getHeader("Authorization");
        if (headerAttribute == null) {
            return null;
        }
        if (headerAttribute.startsWith("Bearer")) {
            return headerAttribute.substring(7);
        }
        return headerAttribute;
    }

    Authentication validateToken(String token) {
        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey("jsonwebtokenforsiiriweatherapisecretformoresecuritythanpreviously")
                .build()
                .parse(token)
                .getBody();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new MyUser(claims.get("id", Long.class), claims.get("username", String.class), "", grantedAuthorityList);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
