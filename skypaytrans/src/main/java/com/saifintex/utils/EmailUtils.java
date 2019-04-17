package com.saifintex.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils { 
	
	
	public static void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		JavaMailSenderImpl sender=new JavaMailSenderImpl();

		sender.setHost("smtp.gmail.com");
		sender.setPort(465);
		sender.setUsername("info.saifintex@gmail.com");
		sender.setPassword("saifin.saipro@123");
		sender.setProtocol("smtp");
		sender.setJavaMailProperties(getMailProperties());		
		message.setTo(to);
		message.setSubject(subject);		
		message.setText(text);	
		sender.send(message);
	}
	
private static Properties getMailProperties() {
    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.smtp.auth", "false");
    /*properties.setProperty("mail.smtp.starttls.enable", "false");*/
    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.setProperty("mail.debug", "false");
    return properties;
}


public static void sendHTMLEmail(String to, String cc,String subject, String text,String html) {
	
	JavaMailSenderImpl sender=new JavaMailSenderImpl();
	MimeMessage message = sender.createMimeMessage();
	
	sender.setHost("smtp.gmail.com");
	sender.setPort(465);
	sender.setUsername("info.saifintex@gmail.com");
	sender.setPassword("saifin.saipro@123");
	sender.setProtocol("smtp");
	sender.setJavaMailProperties(getMailProperties());	
	
	try {
		MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
		helper.setTo(to);
		helper.setSubject(subject);
helper.setCc(cc);

		helper.setText(html,true);
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	sender.send(message);
}



public static void sendHTMLEmailWithAttachement(String[] to, String[] cc,String subject, String text,String html,String attachementFileName,File attachementFile) throws MessagingException {
	
	JavaMailSenderImpl sender=new JavaMailSenderImpl();
	MimeMessage message = sender.createMimeMessage();
	
	sender.setHost("smtp.gmail.com");
	sender.setPort(465);
	sender.setUsername("info.saifintex@gmail.com");
	sender.setPassword("saifin.saipro@123");
	sender.setProtocol("smtp");
	sender.setJavaMailProperties(getMailProperties());	
	

		MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
		helper.setTo(to);
		helper.setSubject(subject);
helper.setCc(cc);
helper.addAttachment(attachementFileName, attachementFile);
		helper.setText(text);
	
		
	sender.send(message);
}














}
