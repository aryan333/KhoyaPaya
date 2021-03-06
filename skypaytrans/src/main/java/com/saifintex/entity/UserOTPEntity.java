package com.saifintex.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UsersOTP")
public class UserOTPEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "MobileNumber", length = 15,unique=true,nullable=false)
	private String mobileNumber;

	@Column(name = "GeneratedOTP")
	private int generatedOTP;

	@Column(name = "TotalOTPCount")
	private int totalOTPCount;

	@Column(name = "TodayOTPCount")
	private int todayOTPCount;

	@Column(name = "LastOTPGenDate")
	private Date lastOTPGenDate;

	@Column (name="OTPTriedCount")
	private int numberOfOTPTried;
	
	@Column(name="OTPBlockedDateTime")
	private Date otpBlockedDateTime;
	
	@Column(name="IsOTPExpired")
	private boolean otpExpired=false;
	@Column(name="OTPSendBlockedDateTime")
	private Date otpSendBlockedDateTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getGeneratedOTP() {
		return generatedOTP;
	}

	public void setGeneratedOTP(int generatedOTP) {
		this.generatedOTP = generatedOTP;
	}

	public int getTotalOTPCount() {
		return totalOTPCount;
	}

	public void setTotalOTPCount(int totalOTPCount) {
		this.totalOTPCount = totalOTPCount;
	}

	public int getTodayOTPCount() {
		return todayOTPCount;
	}

	public void setTodayOTPCount(int todayOTPCount) {
		this.todayOTPCount = todayOTPCount;
	}

	public Date getLastOTPGenDate() {
		return lastOTPGenDate;
	}

	public void setLastOTPGenDate(Date lastOTPGenDate) {
		this.lastOTPGenDate = lastOTPGenDate;
	}

	public int getNumberOfOTPTried() {
		return numberOfOTPTried;
	}

	public void setNumberOfOTPTried(int numberOfOTPTried) {
		this.numberOfOTPTried = numberOfOTPTried;
	}

	public Date getOtpBlockedDateTime() {
		return otpBlockedDateTime;
	}

	public void setOtpBlockedDateTime(Date otpBlockedDateTime) {
		this.otpBlockedDateTime = otpBlockedDateTime;
	}

	public boolean isOtpExpired() {
		return otpExpired;
	}

	public void setOtpExpired(boolean otpExpired) {
		this.otpExpired = otpExpired;
	}

	public Date getOtpSendBlockedDateTime() {
		return otpSendBlockedDateTime;
	}

	public void setOtpSendBlockedDateTime(Date otpSendBlockedDateTime) {
		this.otpSendBlockedDateTime = otpSendBlockedDateTime;
	}
	
	

}
