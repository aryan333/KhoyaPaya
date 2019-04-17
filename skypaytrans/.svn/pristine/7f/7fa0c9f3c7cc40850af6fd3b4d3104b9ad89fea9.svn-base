package com.saifintex.utils;

public class EmailThread implements Runnable {

	private String to;
	private String subject;
	private String text;	
	
	public EmailThread(String to, String subject, String text) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	@Override
	public void run() {
		EmailUtils.sendEmail(to, subject, text);		
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
}
