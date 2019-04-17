package com.saifintex.web.domain;


import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.annotation.NarrationSize;
import com.saifintex.common.Number2StringDesrializer;
import com.saifintex.domain.BaseDomain;
import com.saifintex.utils.JsonDateSerializer;

 
public class Transactions extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Domain to create transaction @Ajay
	 */

	private long payId;

	private int payerId;

	private int payeeId;

	private BigDecimal amount;
	@NarrationSize
	private String narration;

	private Date paymentDate;

	@NotBlank
	private String paymentStatus;

	@Digits(integer = 8, fraction = 2)
	@DecimalMin("0.00")
	private BigDecimal billAmount;

	private BigDecimal cashReceived;

	private BigDecimal skyCredit = BigDecimal.ZERO;

	private BigDecimal previousBalance = BigDecimal.ZERO;

	private String transactionType;

	private String transactionId;

	private int initiatedFrom;

	private String recipientMobileNumber;
	private String recipientName;
	private String recipientBlobId;
	private String latitude;

	private String longitude;

	private String confirmLatitude;

	private String confirmLongitude;

	private String category;

	private int userId;
	private String userName;

	private long transactionDuration;

	private long tempTransTimeLimit;

	private Boolean gstEnabled;
	
	private BigDecimal currTranPrevBal;
	private String userType;
	private String payerName;
	private String payeeName;
	private long totalRecords;
	private boolean isGSTEnabled;
	
	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getPayId() {
		return payId;
	}

	public void setPayId(long payId) {
		this.payId = payId;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = JsonDateSerializer.class)
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getCashReceived() {
		return cashReceived;
	}

	public void setCashReceived(BigDecimal cashReceived) {
		this.cashReceived = cashReceived;
	}

	public BigDecimal getSkyCredit() {
		return skyCredit;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public void setSkyCredit(BigDecimal skyCredit) {
		this.skyCredit = skyCredit;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getInitiatedFrom() {
		return initiatedFrom;
	}

	public void setInitiatedFrom(int initiatedFrom) {
		this.initiatedFrom = initiatedFrom;
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

	public String getConfirmLatitude() {
		return confirmLatitude;
	}

	public void setConfirmLatitude(String confirmLatitude) {
		this.confirmLatitude = confirmLatitude;
	}

	public String getConfirmLongitude() {
		return confirmLongitude;
	}

	public void setConfirmLongitude(String confirmLongitude) {
		this.confirmLongitude = confirmLongitude;
	}

	public String getRecipientMobileNumber() {
		return recipientMobileNumber;
	}

	public void setRecipientMobileNumber(String recipientMobileNumber) {
		this.recipientMobileNumber = recipientMobileNumber;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientBlobId() {
		return recipientBlobId;
	}

	public void setRecipientBlobId(String recipientBlobId) {
		this.recipientBlobId = recipientBlobId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getTransactionDuration() {
		return transactionDuration;
	}

	public void setTransactionDuration(long transactionDuration) {
		this.transactionDuration = transactionDuration;
	}

	public long getTempTransTimeLimit() {
		return tempTransTimeLimit;
	}

	public void setTempTransTimeLimit(long tempTransTimeLimit) {
		this.tempTransTimeLimit = tempTransTimeLimit;
	}

	
	public Boolean getGstEnabled() {
		return gstEnabled;
	}

	public void setGstEnabled(Boolean gstEnabled) {
		this.gstEnabled = gstEnabled;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getCurrTranPrevBal() {
		return currTranPrevBal;
	}

	public void setCurrTranPrevBal(BigDecimal currTranPrevBal) {
		this.currTranPrevBal = currTranPrevBal;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isGSTEnabled() {
		return isGSTEnabled;
	}

	public void setGSTEnabled(boolean isGSTEnabled) {
		this.isGSTEnabled = isGSTEnabled;
	}



}
