package com.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CustomUserDetailsService userDetailsService;
	private final CustomPersistentTokenRepository customPersistentTokenRepository;
	public WebSecurityConfig(CustomUserDetailsService userDetailsService,
			CustomPersistentTokenRepository customPersistentTokenRepository) {
		this.userDetailsService = userDetailsService;
		this.customPersistentTokenRepository = customPersistentTokenRepository;
	}
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(new CustomPasswordEncoder());
	}  
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        		.antMatchers("/signup","/login","/emailVerification/**").permitAll()
        		.antMatchers("/admin/**").hasRole("ADMIN")
        		.anyRequest().authenticated()
            .and()
            	.formLogin()
            	.loginPage("/login")
            	.defaultSuccessUrl("/")
            	.failureHandler(new CustomAuthenticationFailureHandler())
            .and()
            	.logout()
            	.logoutUrl("/logout")
            	.logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .and()
            	.rememberMe()
            	.rememberMeParameter("remember-me")
            	.tokenRepository(customPersistentTokenRepository)
            	.userDetailsService(userDetailsService)
            	.tokenValiditySeconds(604800);
        
        http.csrf().disable();
	}

}
