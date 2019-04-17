package com.saifintex.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.saifintex.entity.DisputeReasonsEntity;

public class DisputesDTO extends BaseDto implements Serializable {
	

	
	private int id;
	
	private Date disputeDate;
   
   private String disputeStatus;
   
   private int userId;
   
   private long payId;
   
   private Set<DisputeReasonsDTO> reasons;
   
  public Set<DisputeReasonsDTO> getReasons() {
	return reasons;
}

public void setReasons(Set<DisputeReasonsDTO> reasons) {
	this.reasons = reasons;
}

/* private int disputeReasonId;
   
   public int getDisputeReasonId() {
	return disputeReasonId;
}

public void setDisputeReasonId(int disputeReasonId) {
	this.disputeReasonId = disputeReasonId;
}
*/
public long getPayId() {
	return payId;
}

public void setPayId(long payId) {
	this.payId = payId;
}

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

private BigDecimal totalAmount;
	
   private BigDecimal cash;
   
   private BigDecimal skyCredit;

   
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
