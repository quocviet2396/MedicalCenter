package com.medicalcenterportal.mailer;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.medicalcenter.entities.Contact;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("EmailService")
public class EmailServiceImpl implements EmailService {

	private static final String NOREPLY_ADDRESS = "noreply@thebags.com";

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SimpleMailMessage template;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	@Value("classpath:static/assets/img/logo.png")
	private Resource resourceFile;

	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(NOREPLY_ADDRESS);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void sendSimpleMessageUsingTemplate(String to, String subject, Object... templateModel) {
		System.out.println(template.getText());
		String text = String.format(template.getText(), templateModel);
		sendSimpleMessage(to, subject, text);
	}

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			// pass 'true' to the constructor to create a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(NOREPLY_ADDRESS);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);

			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);

			emailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel)
			throws MessagingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);

		String htmlBody = thymeleafTemplateEngine.process("emails/template-newuser.html", thymeleafContext);

		sendHtmlMessage(to, subject, htmlBody);
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(NOREPLY_ADDRESS);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		helper.addInline("logo.png", resourceFile);
		emailSender.send(message);
	}
	@Override
	public void sendEmailToContact(Contact contact, String subject, String body) {
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(contact.getEmail());
	        message.setSubject(subject);
	        message.setText(body);
	        emailSender.send(message);
	}
}