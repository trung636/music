package com.example.musicmanager.config;

import com.example.musicmanager.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String username = authentication.getPrincipal().toString();
            String role = populateAuthorities(authentication.getAuthorities());
            User user = new User();
            user.setUsername(username);
            user.setRole(role);
            String tokenJwt = createTokenJwt(user);
            response.setHeader("token", tokenJwt);
        }
        filterChain.doFilter(request, response);
    }

    public String createTokenJwt(User user) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis()+ 10*60*1000);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .claim("role", user.getRole())
                .compact();
    }
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection){

        List<String> authentications = new ArrayList<>();
        for(GrantedAuthority authority : collection){
            authentications.add(authority.getAuthority());
        }

        return String.join(",", authentications);
    }

}
