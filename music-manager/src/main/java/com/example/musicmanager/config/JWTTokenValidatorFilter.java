package com.example.musicmanager.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Component
public class JWTTokenValidatorFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest sr,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        String token = request.getHeader("token");
        if (token != null) {
            try {
                System.out.println("đã vào");
                Claims claims = Jwts.parser()
                        .setSigningKey("secret")
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(username, null, setAuthority(role));
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(principal);
            } catch (Exception e) {
                    throw new BadCredentialsException("Invalid Token received!!!");
            }
        }
        filterChain.doFilter(request,response);
    }

    protected List<GrantedAuthority> setAuthority(String auth) {
        List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
        AUTHORITIES.add(new SimpleGrantedAuthority(auth));
        return AUTHORITIES;
    }


}
