package com.saifintex.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.utils.JsonDateSerializer;
public class BaseDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private int createdBy;
	

	private Date createdOn;
	
	
	private int modifiedBy;
	
	
	private Date modifiedOn;

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	
	
	
}
