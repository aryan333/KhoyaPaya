package com.saifintex.web.domain;

import java.math.BigDecimal;

public class TransactionStatsDashboard {

	private long totalTrans;
	private BigDecimal totalTransAmount;
	private long totalPendingTrans;
	private BigDecimal totalPendingTransAmount;
	private long totalBusinessTransaction;
	private long totalFriendTransactons;
	
	public long getTotalTrans() {
		return totalTrans;
	}
	public void setTotalTrans(long totalTrans) {
		this.totalTrans = totalTrans;
	}
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
	
}
