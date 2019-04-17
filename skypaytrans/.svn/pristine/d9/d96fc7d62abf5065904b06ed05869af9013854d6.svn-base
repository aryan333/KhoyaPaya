package com.saifintex.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.common.Number2StringDesrializer;

public class TransactionSummaryParams {
	private BigDecimal totalPurBillAmount;
	private BigDecimal totalSalBillAmount;
	private BigDecimal billBalance;
	private BigDecimal totalIPaid;
	private BigDecimal totalIReceived;
	private BigDecimal balance;
	private double netBalance;
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalPurBillAmount() {
		return totalPurBillAmount;
	}

	public void setTotalPurBillAmount(BigDecimal totalPurBillAmount) {
		this.totalPurBillAmount = totalPurBillAmount;
	}
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalSalBillAmount() {
		return totalSalBillAmount;
	}

	public void setTotalSalBillAmount(BigDecimal totalSalBillAmount) {
		this.totalSalBillAmount = totalSalBillAmount;
	}
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getBillBalance() {
		return billBalance;
	}

	public void setBillBalance(BigDecimal billBalance) {
		this.billBalance = billBalance;
	}
	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)

	public BigDecimal getTotalIPaid() {
		return totalIPaid;
	}

	public void setTotalIPaid(BigDecimal totalIPaid) {
		this.totalIPaid = totalIPaid;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getTotalIReceived() {
		return totalIReceived;
	}

	public void setTotalIReceived(BigDecimal totalIReceived) {
		this.totalIReceived = totalIReceived;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getNetBalance() {
		return new BigDecimal(String.valueOf(this.netBalance));
	}

	public void setNetBalance(double netBalance) {
		this.netBalance = netBalance;
	}

}
