package com.saifintex.domain;

import java.io.Serializable;

public class DisputeReasons  extends BaseDomain implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private int disputeReasonId;
	
	
	private String disputeReason;
	

	private String disputeReasDes;
	
	private boolean active;

	public int getDisputeReasonId() {
		return disputeReasonId;
	}

	public void setDisputeReasonId(int disputeReasonId) {
		this.disputeReasonId = disputeReasonId;
	}

	public String getDisputeReason() {
		return disputeReason;
	}

	public void setDisputeReason(String disputeReason) {
		this.disputeReason = disputeReason;
	}

	public String getDisputeReasDes() {
		return disputeReasDes;
	}

	public void setDisputeReasDes(String disputeReasDes) {
		this.disputeReasDes = disputeReasDes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	
}
