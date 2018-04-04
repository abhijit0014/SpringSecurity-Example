package com.spring.security.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.security.entity.User;
import com.spring.security.entity.VerificationToken;
import com.spring.security.repository.VerificationTokenRepository;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true) // for data fetch
public class VerificationTokenService {

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	public VerificationToken save(VerificationToken verificationToken) {
		return verificationTokenRepository.save(verificationToken);
	}
	
	public User verifyToken(String token) {
		
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token).get(0);
		java.sql.Date currentDate = new java.sql.Date(new java.util.Date().getTime());
		
		if (verificationToken.getExpiryDate().after(currentDate)) {
			return verificationToken.getUser();
		}else {
			updateToken(verificationToken);
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false) // for data insert
	public VerificationToken updateToken(VerificationToken verificationToken) {
		verificationToken.setToken();
		verificationToken.setExpiryDate();
		return verificationTokenRepository.save(verificationToken);
	}	
}
