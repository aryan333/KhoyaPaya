package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DisputeReasons")

public class DisputeReasonsEntity extends BaseEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DisputeReasonId")
	private int disputeReasonId;
	
	@Column(name="DisputeReason")
	private String disputeReason;
	
	@Column(name="DisputeReasDes")
	private String disputeReasDes;
	
	@Column(name="isActive")
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
