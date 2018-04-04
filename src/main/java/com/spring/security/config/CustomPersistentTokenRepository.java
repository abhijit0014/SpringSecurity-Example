package com.spring.security.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.entity.PersistentLogins;
import com.spring.security.service.PersistentLoginService;
@Repository
public class CustomPersistentTokenRepository implements PersistentTokenRepository {
	
	@Autowired
	private PersistentLoginService persistentLoginService;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
	    PersistentLogins logins = new PersistentLogins();
	    logins.setUsername(token.getUsername());
	    logins.setSeries(token.getSeries());
	    logins.setToken(token.getTokenValue());
	    logins.setLastUsed(token.getDate());
	    persistentLoginService.save(logins);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
	    PersistentLogins logins = persistentLoginService.getBySeriesId(seriesId);
	      
	      if (logins != null) {
	    	  return new PersistentRememberMeToken(logins.getUsername(), 
	    			  logins.getSeries(), logins.getToken(),logins.getLastUsed());
	      }
	      return null;
	}

	@Override
	public void removeUserTokens(String username) {
		persistentLoginService.delete(username);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentLogins logins = persistentLoginService.getBySeriesId(series);
	      if (logins != null) {
	    	  logins.setToken(tokenValue);
	    	  logins.setLastUsed(lastUsed);
	    	  persistentLoginService.save(logins);
	      }		
	}

}
