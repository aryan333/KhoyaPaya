package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name="BillNumberSeries")
@DynamicInsert
@DynamicUpdate
public class BillNumberSeriesEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	
	@Column(name="UserId")
	private int userId;
	
	@Column(name="IncrementValue")
	private long incrementValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(long incrementValue) {
		this.incrementValue = incrementValue;
	}
	

}
