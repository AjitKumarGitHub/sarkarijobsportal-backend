package com.marbel.job.config;

import com.marbel.job.utills.JwtRequestFilter;
import com.marbel.job.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// Public endpoints
				.requestMatchers(
                        "/sitemap.xml",
                        "/jobs-sitemap.xml",
                        "/results-sitemap.xml",
                        "/admit-sitemap.xml",
                        "/blogs-sitemap.xml",
                        "/schemes-sitemap.xml"
                ).permitAll()
				.requestMatchers("/actuator/health","/health").permitAll()
				.requestMatchers("/api/notification/subscribe", "/api/notification/allsubscribers").permitAll()
				.requestMatchers("/api/v1/comments/**").permitAll()
				.requestMatchers("/api/v1/admin/login", "/api/v1/admin/signup").permitAll()
				 .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
	                
	                // Restrict modification operations to authenticated users (admins)
	                .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()  // For add operations
	                .requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()   // For update operations
	                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated() // For delete operations
	                
				// Any other request — require authentication
				.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
