package com.saifintex.dto;

import java.math.BigDecimal;

public class TransactionSummaryParamsDto {
	private BigDecimal totalPurBillAmount;
	private BigDecimal totalSalBillAmount;
	private BigDecimal totalIPaid;
	private BigDecimal totalIReceived;
	private double netBalance;
	private BigDecimal billBalance;
	private BigDecimal balance;

	public BigDecimal getBillBalance() {
		if (this.billBalance == null) {
			return new BigDecimal("0.00");
		}
		return billBalance;
	}

	public void setBillBalance(BigDecimal billBalance) {
		this.billBalance = billBalance;
	}

	public BigDecimal getBalance() {
		if (this.balance == null) {
			return new BigDecimal("0.00");
		}
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTotalIPaid() {
		if (this.totalIPaid == null) {
			return new BigDecimal("0.00");
		}
		return totalIPaid;
	}

	public void setTotalIPaid(BigDecimal totalIPaid) {
		this.totalIPaid = totalIPaid;
	}

	public BigDecimal getTotalIReceived() {
		if (this.totalIReceived == null) {
			return new BigDecimal("0.00");
		}
		return totalIReceived;
	}

	public void setTotalIReceived(BigDecimal totalIReceived) {
		this.totalIReceived = totalIReceived;
	}

	public double getNetBalance() {

		return netBalance;
	}

	public void setNetBalance(double netBalance) {
		this.netBalance = netBalance;
	}

	public BigDecimal getTotalPurBillAmount() {
		if (this.totalPurBillAmount == null) {
			return new BigDecimal("0.00");
		}
		return totalPurBillAmount;
	}

	public void setTotalPurBillAmount(BigDecimal totalPurBillAmount) {
		this.totalPurBillAmount = totalPurBillAmount;
	}

	public BigDecimal getTotalSalBillAmount() {
		if (this.totalSalBillAmount == null) {
			return new BigDecimal("0.00");
		}
		return totalSalBillAmount;
	}

	public void setTotalSalBillAmount(BigDecimal totalSalBillAmount) {
		this.totalSalBillAmount = totalSalBillAmount;
	}

}
