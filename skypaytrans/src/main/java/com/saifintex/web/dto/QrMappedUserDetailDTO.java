package com.saifintex.web.dto;

import java.io.Serializable;
import java.util.Date;

public class QrMappedUserDetailDTO implements Serializable {

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
