package com.saifintex.dto;

import java.util.Date;

public class DashboardDataDateParams {
	
	private Date yesterdayDate;
	private Date startingDateOfWeek;
	private Date endDateOfWeek;
	private Date startingDate;
	private Date uptoDate;
	
	public Date getYesterdayDate() {
		return yesterdayDate;
	}
	public void setYesterdayDate(Date yesterdayDate) {
		this.yesterdayDate = yesterdayDate;
	}
	public Date getStartingDateOfWeek() {
		return startingDateOfWeek;
	}
	public void setStartingDateOfWeek(Date startingDateOfWeek) {
		this.startingDateOfWeek = startingDateOfWeek;
	}
	public Date getEndDateOfWeek() {
		return endDateOfWeek;
	}
	public void setEndDateOfWeek(Date endDateOfWeek) {
		this.endDateOfWeek = endDateOfWeek;
	}
	public Date getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}
	public Date getUptoDate() {
		return uptoDate;
	}
	public void setUptoDate(Date uptoDate) {
		this.uptoDate = uptoDate;
	}

	
}
