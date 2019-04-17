package com.saifintex.entity;

import java.io.Serializable;
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
@Table(name="SaiCards")
public class SaiCardsEntity extends BaseEntity implements Serializable {

	/**
	 * @Ajay Its a entity to persist sai card detail of a user
	 */

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SaiCardId")
	private int saiCardId;
	
	@Column(name="SaiCardNumber")
	private long saiCardNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SaiCardIssueDate")
	private Date saiCardIssueDate;
	
	@Column(name="SaiCardStatus",length=10)
	private String saiCardStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ValidUpto")
	private Date validUpto;

	public int getSaiCardId() {
		return saiCardId;
	}

	public void setSaiCardId(int saiCardId) {
		this.saiCardId = saiCardId;
	}

	
	public long getSaiCardNumber() {
		return saiCardNumber;
	}

	public void setSaiCardNumber(long saiCardNumber) {
		this.saiCardNumber = saiCardNumber;
	}

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

	public Date getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}
	
	

}
