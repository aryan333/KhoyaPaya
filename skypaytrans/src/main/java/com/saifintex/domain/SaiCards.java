package com.saifintex.domain;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.utils.JsonDateSerializer;

public class SaiCards extends BaseDomain {
	
	private int saiCardId;
	
	
	private long saiCardNumber;
	
	
	private Date saiCardIssueDate;
	
	
	private String saiCardStatus;
	
	
	private Date validUpto;

	public int getSaiCardId() {
		return saiCardId;
	}

	public void setSaiCardId(int saiCardId) {
		this.saiCardId = saiCardId;
	}

	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getSaiCardIssueDate() {
		return saiCardIssueDate;
	}

	public void setSaiCardIssueDate(Date saiCardIssueDate) {
		this.saiCardIssueDate = saiCardIssueDate;
	}

	public String getSaiCardStatus() {
		return saiCardStatus;
	}

	public void setSaiCardStatus(String saiCardStatus) {
		this.saiCardStatus = saiCardStatus;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}

	public long getSaiCardNumber() {
		return saiCardNumber;
	}

	public void setSaiCardNumber(long saiCardNumber) {
		this.saiCardNumber = saiCardNumber;
	}
	

	
}
