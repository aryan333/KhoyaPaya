package com.saifintex.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="UsersTotalEarnedPoints")
public class UsersTotalEarnedPointsEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UsersTotalEarnedPointsId")
	private int id;
	
	@Column(name="TotalPointsEarned",length=8)
	private int totalPointsEarned;
	
	@Column(name="ValidUpto")
	@Temporal(TemporalType.TIMESTAMP)
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
