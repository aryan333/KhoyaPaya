package com.saifintex.domain;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.utils.JsonDateSerializer;


public class UserOTP extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @ Author Ajay 
	 * UserOTP Dto
	 */
	

	private long id;

	
	private String mobileNumber;

	
	private int generatedOTP;

	
	private int totalOTPCount;

	
	private int todayOTPCount;

	
	private Date lastOTPGenDate;
	
	private int numberOfOTPTried;
	
	private Date otpBlockedDateTime;
	
	private boolean otpExpired;

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
	@JsonSerialize(using=JsonDateSerializer.class)
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
	
	

}
