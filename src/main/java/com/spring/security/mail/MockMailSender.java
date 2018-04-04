package com.spring.security.mail;

import java.net.ConnectException;

import javax.mail.MessagingException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class MockMailSender implements MailSender {

	@Override
	public void send(String to, String subject, String body) throws MessagingException, ConnectException {
		System.out.println(body);
	}

}
