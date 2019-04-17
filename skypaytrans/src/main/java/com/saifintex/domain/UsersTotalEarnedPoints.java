package com.saifintex.domain;

import java.util.Date;

public class UsersTotalEarnedPoints extends BaseDomain{
	
	
	private int id;	
	private int totalPointsEarned;
	private Date validUpto;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalPointsEarned() {
		return totalPointsEarned;
	}
	public void setTotalPointsEarned(int totalPointsEarned) {
		this.totalPointsEarned = totalPointsEarned;
	}
	public Date getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}
	
	
}
