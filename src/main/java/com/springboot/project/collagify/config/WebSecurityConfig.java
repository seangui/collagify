package com.springboot.project.collagify.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
			.antMatchers("/", "/css/**", "/images/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.oauth2Login()
			.loginPage("/")
			/*
			 *  the second parameter makes it such that the user 
			 *  will always land on the /home page after authentication 
			 *	regardless if they visited a secured page before authentication. 
			 */
			.defaultSuccessUrl("/set-up", true)
			.failureUrl("/login-failed"); 		
	}
}
