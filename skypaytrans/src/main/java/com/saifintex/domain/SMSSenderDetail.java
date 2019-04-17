package com.saifintex.domain;

import java.util.List;

public class SMSSenderDetail {
	private String sender;
	
	private List<SMSDetailToInvite> sms;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public List<SMSDetailToInvite> getSms() {
		return sms;
	}

	public void setSms(List<SMSDetailToInvite> sms) {
		this.sms = sms;
	}

	
	

}
