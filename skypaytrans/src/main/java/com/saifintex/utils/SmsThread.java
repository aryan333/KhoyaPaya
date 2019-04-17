package com.saifintex.utils;

public class SmsThread implements Runnable {

	private String to;
	private String message;
	
	public SmsThread(String to,String message) {
		this.to=to;
		this.message=message;
	}
	@Override
	public void run() {
		FcmNotificationUtil.sendSms(message, to, null);
		
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
