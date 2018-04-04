package com.spring.security.service;

import java.net.ConnectException;

import javax.mail.MessagingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.security.entity.User;
import com.spring.security.entity.VerificationToken;
import com.spring.security.mail.MailSender;
import com.spring.security.repository.UserRepository;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true) // for data fetch
public class UserService {

	private final UserRepository userRepository;
	private final VerificationTokenService verificationTokenService;
	private final MailSender mailSender;
	public UserService(UserRepository userRepository, VerificationTokenService verificationTokenService,
			MailSender mailSender) {
		this.userRepository = userRepository;
		this.verificationTokenService = verificationTokenService;
		this.mailSender = mailSender;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false) // for data insert
	public Boolean register(User user){
		
        if (emailExist(user.getEmail())) {
            return false;
        }
        
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(false);
		User user2 = userRepository.save(user);
		
		VerificationToken vToken = new VerificationToken();
		vToken.setExpiryDate();
		vToken.setToken();
		vToken.setUser(user2);
		verificationTokenService.save(vToken);
		
		sendVerificationEmail(vToken);
		
		return true;
	}
	
	private void sendVerificationEmail(VerificationToken vToken){
		
		String to = vToken.getUser().getEmail();
		String subject = "Email Verification";
		String body = "http://localhost:8181/emailVerification/"+vToken.getToken();
		
		try {
			mailSender.send(to, subject, body);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
    public boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null)
            return true;
        else
        	return false;
    }
    
    public void enable(Long id) {
    	userRepository.enable(id);
    }
	
}
