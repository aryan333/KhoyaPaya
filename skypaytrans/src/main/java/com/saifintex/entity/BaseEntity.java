package com.saifintex.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract  class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="CreatedBy",nullable=true)
	private int createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreatedOn",nullable=true)
	private Date createdOn;
	
	@Column(name="ModifiedBy",nullable=true)
	private int modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ModifiedOn")
	private Date modifiedOn;

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	
	
	
}
