package com.saifintex.domain;


import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.common.Number2StringDesrializer;


public class TransactionNotificationParam {	
	
	private int customerId;
	private int payId;
	private int merchantId;
	@JsonSerialize(using = Number2StringDesrializer.class)
	private BigDecimal cashReceived;
	@JsonSerialize(using = Number2StringDesrializer.class)
	private BigDecimal skyCredit;
	@JsonSerialize(using = Number2StringDesrializer.class)
	private BigDecimal billAmount;

	private String initiatedFrom;
	private String transactionDate;
	private String userName1;
	private String userName2;
	private String paymentStatus;
	@JsonSerialize(using = Number2StringDesrializer.class)
	private BigDecimal previousBalance;
	private String description;
	private String createdAt;
	private String modifiedAt;

	private String transactionId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}	

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public BigDecimal getCashReceived() {
		return cashReceived;
	}

	public void setCashReceived(BigDecimal cashReceived) {
		this.cashReceived = cashReceived;
	}

	public BigDecimal getSkyCredit() {
		return skyCredit;
	}

	public void setSkyCredit(BigDecimal skyCredit) {
		this.skyCredit = skyCredit;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public String getInitiatedFrom() {
		return initiatedFrom;
	}

	public void setInitiatedFrom(String initiatedFrom) {
		this.initiatedFrom = initiatedFrom;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	

}
