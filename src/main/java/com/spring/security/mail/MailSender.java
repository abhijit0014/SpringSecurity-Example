package com.spring.security.mail;

import java.net.ConnectException;

import javax.mail.MessagingException;

public interface MailSender {
	public void send(String to, String subject, String body)throws MessagingException, ConnectException;
}
