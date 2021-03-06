package com.saifintex.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.annotation.NarrationSize;
import com.saifintex.common.Number2StringDesrializer;
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

	private boolean isGSTEnabled;

	private BigDecimal currTranPrevBal;

	private BigDecimal currentPaymentStatus;

	private String userType;

	private String mappedName;

	private boolean inviteEnabled;
	
	private int points;
	@JsonIgnore
	private CommonsMultipartFile file;
	
	private String invoiceBlobId;
	
	//private String billNumber;
	/*private boolean isEnabled;
	private boolean isAccountNonLocked;
	private boolean isAccountNonExpired;
	private boolean isCredentialsNonExpired;
*/
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

	public boolean isGSTEnabled() {
		return isGSTEnabled;
	}

	public void setGSTEnabled(boolean isGSTEnabled) {
		this.isGSTEnabled = isGSTEnabled;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getCurrTranPrevBal() {
		return currTranPrevBal;
	}

	public void setCurrTranPrevBal(BigDecimal currTranPrevBal) {
		this.currTranPrevBal = currTranPrevBal;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
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

	/*public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}
*/
	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public boolean isInviteEnabled() {
		return inviteEnabled;
	}

	public void setInviteEnabled(boolean inviteEnabled) {
		this.inviteEnabled = inviteEnabled;
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

	

	/*public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
*/
	
	

}
