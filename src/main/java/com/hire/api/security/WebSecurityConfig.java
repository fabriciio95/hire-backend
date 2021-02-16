package com.hire.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors()
			.and()
				.headers().frameOptions().disable()
			.and()
				.httpBasic()
			.and()
				.authorizeRequests()
					.antMatchers("/profissional/perfil/*").authenticated()
					.antMatchers("/profissional/*").authenticated()
					.antMatchers("/usuario/*").authenticated()
					.antMatchers("/avaliacao/**").authenticated()
					.anyRequest().permitAll()
			.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("POST", "GET", "PUT", "DELETE")
			.exposedHeaders(SecurityConstants.AUTHORIZATION_HEADER);
	}
	
}
