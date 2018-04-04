package com.spring.security.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler  {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, 
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			getRedirectStrategy().sendRedirect(request, response, "/login?badCredential");
		} else if (exception.getClass().isAssignableFrom(RememberMeAuthenticationException.class)) {
			getRedirectStrategy().sendRedirect(request, response, "/login?rememberMe");
		} else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {
			getRedirectStrategy().sendRedirect(request, response, "/login?session");
		} else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			getRedirectStrategy().sendRedirect(request, response, "/login?disabled");
		}
		
		//super.onAuthenticationFailure(request, response, exception);
	}
}
