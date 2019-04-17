package com.saifintex.web.domain;


import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.common.Number2StringDesrializer;


public class TransactionsRecord {

	private long totalTransactios;
	private BigDecimal totalAmount;
	private BigDecimal totalCashFlow;
	private BigDecimal totalCreditFlow;
	private long acceptedTransactions;
	private long rejectedTransactions;
	private long pendingTransactins;
	private long closedTransactions;
	private long tempTransactions;
	private long businessTransactions;
	private long friendTransactions;

	public TransactionsRecord(long totalTransactios, BigDecimal totalAmount, BigDecimal totalCashFlow,
			BigDecimal totalCreditFlow, long acceptedTransactions, long rejectedTransactions, long pendingTransactins,
			long closedTransactions, long tempTransactions) {
		this.totalTransactios = totalTransactios;
		this.totalAmount = totalAmount;
		this.totalCashFlow = totalCashFlow;
		this.totalCreditFlow = totalCreditFlow;
		this.acceptedTransactions = acceptedTransactions;
		this.rejectedTransactions = rejectedTransactions;
		this.pendingTransactins = pendingTransactins;
		this.closedTransactions = closedTransactions;
		this.tempTransactions = tempTransactions;

	}

	public TransactionsRecord() {

	}

	public long getTotalTransactios() {
		return totalTransactios;
	}

	public void setTotalTransactios(long totalTransactios) {
		this.totalTransactios = totalTransactios;
	}
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalCashFlow() {
		return totalCashFlow;
	}

	public void setTotalCashFlow(BigDecimal totalCashFlow) {
		this.totalCashFlow = totalCashFlow;
	}
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalCreditFlow() {
		return totalCreditFlow;
	}

	public void setTotalCreditFlow(BigDecimal totalCreditFlow) {
		this.totalCreditFlow = totalCreditFlow;
	}

	public long getAcceptedTransactions() {
		return acceptedTransactions;
	}

	public void setAcceptedTransactions(long acceptedTransactions) {
		this.acceptedTransactions = acceptedTransactions;
	}

	public long getRejectedTransactions() {
		return rejectedTransactions;
	}

	public void setRejectedTransactions(long rejectedTransactions) {
		this.rejectedTransactions = rejectedTransactions;
	}

	public long getPendingTransactins() {
		return pendingTransactins;
	}

	public void setPendingTransactins(long pendingTransactins) {
		this.pendingTransactins = pendingTransactins;
	}

	public long getClosedTransactions() {
		return closedTransactions;
	}

	public void setClosedTransactions(long closedTransactions) {
		this.closedTransactions = closedTransactions;
	}

	public long getTempTransactions() {
		return tempTransactions;
	}

	public void setTempTransactions(long tempTransactions) {
		this.tempTransactions = tempTransactions;
	}

	public long getBusinessTransactions() {
		return businessTransactions;
	}

	public void setBusinessTransactions(long businessTransactions) {
		this.businessTransactions = businessTransactions;
	}

	public long getFriendTransactions() {
		return friendTransactions;
	}

	public void setFriendTransactions(long friendTransactions) {
		this.friendTransactions = friendTransactions;
	}
	
	

}
