package com.saifintex.domain;

import java.util.Date;

public class UserBalanceRetrievalParams {
private int payerId;
private int payeeId;
private Date date;
private int userId;


public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getPayerId() {
	return payerId;
}
public void setPayerId(int payerId) {
	this.payerId = payerId;
}
public int getPayeeId() {
	return payeeId;
}
public void setPayeeId(int payeeId) {
	this.payeeId = payeeId;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}

}
