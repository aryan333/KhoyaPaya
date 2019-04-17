package com.saifintex.web.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.common.Number2StringDesrializer;
import com.saifintex.utils.JsonDateSerializer;

public class TransactionStatsDashboardDTO {

	private long totalTrans;
	private BigDecimal totalTransAmount;
	private long totalPendingTrans;
	private BigDecimal totalPendingTransAmount;
	private long totalBusinessTransaction;
	private long totalFriendTransactons;
	private long totalAccepted;
	private long totalRejected;
	private Date lastTransacted;
	
	public long getTotalTrans() {
		return totalTrans;
	}
	public void setTotalTrans(long totalTrans) {
		this.totalTrans = totalTrans;
	}
	
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalTransAmount() {
		return totalTransAmount;
	}
	public void setTotalTransAmount(BigDecimal totalTransAmount) {
		this.totalTransAmount = totalTransAmount;
	}
	public long getTotalPendingTrans() {
		return totalPendingTrans;
	}
	public void setTotalPendingTrans(long totalPendingTrans) {
		this.totalPendingTrans = totalPendingTrans;
	}
	
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalPendingTransAmount() {
		return totalPendingTransAmount;
	}
	public void setTotalPendingTransAmount(BigDecimal totalPendingTransAmount) {
		this.totalPendingTransAmount = totalPendingTransAmount;
	}
	
	public long getTotalBusinessTransaction() {
		return totalBusinessTransaction;
	}
	public void setTotalBusinessTransaction(long totalBusinessTransaction) {
		this.totalBusinessTransaction = totalBusinessTransaction;
	}
	public long getTotalFriendTransactons() {
		return totalFriendTransactons;
	}
	public void setTotalFriendTransactons(long totalFriendTransactons) {
		this.totalFriendTransactons = totalFriendTransactons;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getLastTransacted() {
		return lastTransacted;
	}
	public void setLastTransacted(Date lastTransacted) {
		this.lastTransacted = lastTransacted;
	}
	public long getTotalAccepted() {
		return totalAccepted;
	}
	public void setTotalAccepted(long totalAccepted) {
		this.totalAccepted = totalAccepted;
	}
	public long getTotalRejected() {
		return totalRejected;
	}
	public void setTotalRejected(long totalRejected) {
		this.totalRejected = totalRejected;
	}
	
	
}
