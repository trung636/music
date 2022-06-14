package com.example.musicmanager.config;

import com.example.musicmanager.entity.User;
import com.example.musicmanager.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private BCryptPasswordEncoder encode; 


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userRepository.findByUsername(username);
            if(user==null) {
            	throw new UsernameNotFoundException("Check Username!!!!");
            }
            if(!encode.matches(password, user.getPassword())) {
    			throw new BadCredentialsException("Password Fail!!!!");
    		}
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
    
}
