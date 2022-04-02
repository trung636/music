package com.example.musicmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProviderCustom authenticationProviderCustom;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.httpBasic()
				.and()
				.authorizeRequests()
					.antMatchers("/login", "/logout", "/check-login", "/hello", "/my-song/**").permitAll()
					.antMatchers( "/error","/search", "/api/menu/items/**").hasAnyRole("user")
					.anyRequest().authenticated();
		http	.formLogin();

		http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);
		http.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
		http.csrf().disable();
		http.cors().disable();
		http.exceptionHandling().accessDeniedPage("/error");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProviderCustom);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
					.antMatchers("/static/**");

	}
}
