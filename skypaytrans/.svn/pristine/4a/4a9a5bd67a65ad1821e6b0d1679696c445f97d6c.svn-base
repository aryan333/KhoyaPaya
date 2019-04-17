package com.saifintex.utils;

public class OtpSMSThread implements Runnable {

	private String to;
	private String message;
	
	public OtpSMSThread(String to,String message) {
		this.to=to;
		this.message=message;
	}
	@Override
	public void run() {
		FcmNotificationUtil.sendOTPSms(message, to, null);
		
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
