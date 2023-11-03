package com.teqmonic.keycloak.configuration;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.teqmonic.keycloak.configuration.security.JwtAuthConverter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CustomSecurityConfiguration {
	
	@Autowired
	private JwtAuthConverter jwtAuthConverter;

	@Bean
	SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
		
		return http
				.csrf(c -> c.disable())
		        .securityMatcher(antMatcher("/api/**"))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/public/**")).permitAll())
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(
						//server -> server.jwt(Customizer.withDefaults()))
						server -> server.jwt(configurer -> configurer.jwtAuthenticationConverter(jwtAuthConverter)))
		        .build();
	}

}
