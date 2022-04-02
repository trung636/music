package com.example.musicmanager.config;

import com.example.musicmanager.entity.User;
import com.example.musicmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationProviderCustom implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Bean
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	protected List<GrantedAuthority> setAuthority(String auth){
		List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_"+auth));
		return AUTHORITIES;
	}


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		User user = userRepository.findByUsername(username);

		if(user == null) {
			throw new UsernameNotFoundException("Check Username!!!!");
		}
		if(encoder().matches(password, user.getPassword()) != true) {
			throw new BadCredentialsException("Password Fail!!!!");
		}
		UsernamePasswordAuthenticationToken principal= new UsernamePasswordAuthenticationToken(user.getUsername(), password, setAuthority(user.getRole()));
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(principal);
		return principal;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}


}
