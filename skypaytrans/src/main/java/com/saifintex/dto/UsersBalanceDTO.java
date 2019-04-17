package com.saifintex.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UsersBalanceDTO extends BaseDto implements Serializable{

	/**
	 * @Ajay
	 */
	private static final long serialVersionUID = 1L;
	
	private long balanceId;

	private int payerId;
	
	private int payeeId;
	
	private BigDecimal balance;
	
	private BigDecimal totalCash;

	public long getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(long balanceId) {
		this.balanceId = balanceId;
	}

	public long getPayerId() {
		return payerId;
	}

	public void setPayerId(int payerId) {
		this.payerId = payerId;
	}

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(BigDecimal totalCash) {
		this.totalCash = totalCash;
	}
	
	

}
