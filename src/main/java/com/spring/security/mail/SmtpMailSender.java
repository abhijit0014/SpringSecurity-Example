package com.spring.security.mail;

import java.net.ConnectException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender implements MailSender {
	
	@Autowired
	JavaMailSender javaMailSender;

	public void send(String to, String subject, String body) throws MessagingException, ConnectException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setText(body);
		helper.setTo(to);
		javaMailSender.send(message);
	}
}
