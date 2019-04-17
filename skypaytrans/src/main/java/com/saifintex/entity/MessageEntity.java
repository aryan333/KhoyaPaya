package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name="Messages")
public class MessageEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MessageId")
	private int messageId;
	
	@Column(name="Sender",length=50)
	private String sender;
	
	@Column(name="UserId")
	private int    userId;	
	@Lob
	@Column(name="MessageBody")
	private String messageBody;
	@Column(name="AmountType",length=10)
	
	private String type;
	@Column(name="Amount",precision=18, scale=2)
	private String amount;  // this is the transaction amount
	
	
	@Column(name="MessageTime",length=50)
	private String time;
	
	@Column(name="BankName",length=50)
	private String bankName;
	
	@Column(name="AccountBalance",precision=18, scale=2)
	private String balance;       // field for Account Balance
	
	/*@Column(name="CreatedAt")
	private String createdTime;*/
	
	@Column(name="PartyName",length=100)
	private String partyName;
	
	@Column(name="latitude",length=100)
	private String latitude;
	
	@Column(name="longitude",length=100)
	private String longitude;
	
	@Column(name="DeviceID",length=65)
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


}
