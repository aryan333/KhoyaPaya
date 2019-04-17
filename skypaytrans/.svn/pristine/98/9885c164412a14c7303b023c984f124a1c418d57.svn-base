package com.saifintex.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.JoinColumn;



@Entity
@Table(name="Disputes")

public class DisputesEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DisputeId")
	private int id;
	
	@Column(name="UserId")
	private int userId;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name="DisputeDate")
   private Date disputeDate;
   
	@Column(name="DisputeStatus")
   private String disputeStatus;
   
	@Column(name="TotalAmount")
   private BigDecimal totalAmount;
	
   @Column(name="Cash")
   private BigDecimal cash;
   
   @Column(name="SkyCredit")
   private BigDecimal skyCredit; 
   
   @Column(name="PayId")
   private long payId;
   
  /* @Column(name="DisputeReasonId")
   private int disputeReasonId;*/
   @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
   @JoinTable(name="DisputeReasonsMapping",
   joinColumns= @JoinColumn(name="disputeId"),
   inverseJoinColumns=@JoinColumn(name="disputeResId"))
   private Set<DisputeReasonsEntity> reasons;
  

/*public int getDisputeReasonId() {
	return disputeReasonId;
}

public void setDisputeReasonId(int disputeReasonId) {
	this.disputeReasonId = disputeReasonId;
}*/

public long getPayId() {
	return payId;
}

public Set<DisputeReasonsEntity> getReasons() {
	return reasons;
}

public void setReasons(Set<DisputeReasonsEntity> reasons) {
	this.reasons = reasons;
}

public void setPayId(long payId) {
	this.payId = payId;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Date getDisputeDate() {
	return disputeDate;
}

public void setDisputeDate(Date disputeDate) {
	this.disputeDate = disputeDate;
}

public String getDisputeStatus() {
	return disputeStatus;
}

public void setDisputeStatus(String disputeStatus) {
	this.disputeStatus = disputeStatus;
}

public BigDecimal getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}

public BigDecimal getCash() {
	return cash;
}

public void setCash(BigDecimal cash) {
	this.cash = cash;
}

public BigDecimal getSkyCredit() {
	return skyCredit;
}

public void setSkyCredit(BigDecimal skyCredit) {
	this.skyCredit = skyCredit;
}


   

}
