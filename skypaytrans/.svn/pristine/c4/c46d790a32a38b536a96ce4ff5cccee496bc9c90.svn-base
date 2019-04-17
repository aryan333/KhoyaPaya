package com.saifintex.web.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.utils.JsonDateSerializer;

public class QrMappedUserDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String qrSeries;
	private Date qrMappedDate;
	private String mobileNumber;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQrSeries() {
		return qrSeries;
	}
	public void setQrSeries(String qrSeries) {
		this.qrSeries = qrSeries;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getQrMappedDate() {
		return qrMappedDate;
	}
	public void setQrMappedDate(Date qrMappedDate) {
		this.qrMappedDate = qrMappedDate;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	

}
