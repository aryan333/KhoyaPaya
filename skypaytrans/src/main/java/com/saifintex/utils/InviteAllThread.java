package com.saifintex.utils;

public class InviteAllThread implements Runnable {

	private String json;
	
	
	public InviteAllThread(String json) {
		this.json=json;
		
	}
	@Override
	public void run() {
		FcmNotificationUtil.sendJsonSms(json);
		
	}
	
	

}
