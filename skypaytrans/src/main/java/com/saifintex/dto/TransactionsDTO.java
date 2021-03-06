package com.saifintex.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TransactionsDTO extends BaseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long payId;

	private int payerId;

	private int payeeId;

	private BigDecimal amount;

	private String narration;

	private Date paymentDate;

	private String paymentStatus;


	private BigDecimal cashReceived;

	private BigDecimal billAmount;
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

	private long transactionDuration;

	private long tempTransTimeLimit;

	private boolean isGSTEnabled;
	
	private BigDecimal currTranPrevBal;
	
	private BigDecimal currentPaymentStatus;
	
	private String userType;
	
	private String mappedName;
	
	private String billNumber;
	
	private boolean inviteEnabled;
	private String userName;

	private int points;

@JsonIgnore
private CommonsMultipartFile file;

private String invoiceBlobId;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
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

	public boolean isGSTEnabled() {
		return isGSTEnabled;
	}

	public void setGSTEnabled(boolean isGSTEnabled) {
		this.isGSTEnabled = isGSTEnabled;
	}

	public BigDecimal getCurrTranPrevBal() {
		return currTranPrevBal;
	}

	public void setCurrTranPrevBal(BigDecimal currTranPrevBal) {
		this.currTranPrevBal = currTranPrevBal;
	}

	public BigDecimal getCurrentPaymentStatus() {
		return currentPaymentStatus;
	}

	public void setCurrentPaymentStatus(BigDecimal currentPaymentStatus) {
		this.currentPaymentStatus = currentPaymentStatus;
	}

	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public boolean isInviteEnabled() {
		return inviteEnabled;
	}

	public void setInviteEnabled(boolean inviteEnabled) {
		this.inviteEnabled = inviteEnabled;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public String getInvoiceBlobId() {
		return invoiceBlobId;
	}

	public void setInvoiceBlobId(String invoiceBlobId) {
		this.invoiceBlobId = invoiceBlobId;
	}

	
	
	
	
}
