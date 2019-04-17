package com.saifintex.dto;

import java.math.BigDecimal;



public class MessageDto  extends BaseDto implements Comparable<MessageDto> {
	
	
	private String sender;
	
	private int    userId;	
	
	private String messageBody;
	
	private String type;
	
	private String amount;           // this is the transaction amount
	
	private BigDecimal afterParsingAmount;     // after changing the format of String into Big Decimal
	
	private String time;
	
	private String bankName;
	
	private String balance;       // field for Account Balance
	
	private String createdTime;
	
	private String partyName;
	
	private BigDecimal afterParsingBalance;
	
	private String latitude;
	
	private String longitude;
	
	private int messageId;
	
	private String deviceId;


	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public BigDecimal getAfterParsingBalance() {
		return afterParsingBalance;
	}

	public void setAfterParsingBalance(BigDecimal afterParsingBalance) {
		this.afterParsingBalance = afterParsingBalance;
	}

	public BigDecimal getAfterParsingAmount() {
		return afterParsingAmount;
	}

	public void setAfterParsingAmount(BigDecimal afterParsingAmount) {
		this.afterParsingAmount = afterParsingAmount;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public int compareTo(MessageDto o) {
		
		return this.time.compareTo(o.getTime());
	}

}
