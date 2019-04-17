package com.saifintex.web.dto;

public class SalesInfoDTO {
	
	private String salesPersonName;
	private String salesPersonMobileNumber;
	private String slaesPersonInviteCode;
	private long   salesPersonId;
	public String getSalesPersonName() {
		return salesPersonName;
	}
	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}
	public String getSalesPersonMobileNumber() {
		return salesPersonMobileNumber;
	}
	public void setSalesPersonMobileNumber(String salesPersonMobileNumber) {
		this.salesPersonMobileNumber = salesPersonMobileNumber;
	}
	public String getSlaesPersonInviteCode() {
		return slaesPersonInviteCode;
	}
	public void setSlaesPersonInviteCode(String slaesPersonInviteCode) {
		this.slaesPersonInviteCode = slaesPersonInviteCode;
	}
	public long getSalesPersonId() {
		return salesPersonId;
	}
	public void setSalesPersonId(long salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

}
