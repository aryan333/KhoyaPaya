package com.saifintex.domain;

import java.util.List;

public class InviteeDetail {
private String referralCode;
private String userName;
private String phNumber;

private List<UsersContacts> contacts;



public String getReferralCode() {
	return referralCode;
}

public void setReferralCode(String referralCode) {
	this.referralCode = referralCode;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getPhNumber() {
	return phNumber;
}

public void setPhNumber(String phNumber) {
	this.phNumber = phNumber;
}

public List<UsersContacts> getContacts() {
	return contacts;
}

public void setContacts(List<UsersContacts> contacts) {
	this.contacts = contacts;
}





}
