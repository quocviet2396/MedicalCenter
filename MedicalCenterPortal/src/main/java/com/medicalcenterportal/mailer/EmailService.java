package com.medicalcenterportal.mailer;

import java.io.IOException;
import java.util.Map;

import com.medicalcenter.entities.Contact;

import jakarta.mail.MessagingException;

public interface EmailService {
	void sendSimpleMessage(String to, String subject, String text);

	void sendSimpleMessageUsingTemplate(String to, String subject, Object... templateModel);

	void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

	void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel)
			throws IOException, MessagingException;
	 void sendEmailToContact(Contact contact, String subject, String body);
	
}