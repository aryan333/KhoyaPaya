package com.saifintex.utils;

public class SmsPromoThread implements Runnable {

	private String to;
	private String message;
	
	public SmsPromoThread(String to,String message) {
		this.to=to;
		this.message=message;
	}
	@Override
	public void run() {
		FcmNotificationUtil.sendPromoSms(message, to, null);
		
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
