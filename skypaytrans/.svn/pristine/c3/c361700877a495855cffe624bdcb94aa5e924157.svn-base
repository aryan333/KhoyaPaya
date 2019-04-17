package com.saifintex.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.repository.query.parser.Part.Type;


@Entity
@Table(name="UsersBalance")
public final class UsersBalanceEntity extends BaseEntity implements Serializable {
	/**
	 * @Ajay Users Remaining  Balance
	 */
	private static final long serialVersionUID = 1L;		
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BalanceID")
	private long balanceId;
	
	/*@Version
	private Integer version;
	*/
	@Column(name="User1")
	private int user1;
	
	@Column(name="User2")
	private int user2;
	
	@Column(name="Balance",precision=18,scale=2)
	private BigDecimal balance;
	
	@Column(name="TotalCash",precision=18,scale=2)
	private BigDecimal totalCash;

	@Column(name="PendingTransactionsAmount",precision=18,scale=2)
	private BigDecimal pendingTransactionAmount=BigDecimal.ZERO;
	@Column(name="IsTransacted")
	private boolean transacted;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "NameMappingID", nullable = false)
	private UsersNameMapping usersNameMapping;
	
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "UsersInvitationsDetailID", nullable = false)
	private UsersInvitationsDetailEntity usersInvitationsDetailEntity;
	public UsersBalanceEntity() {
		super();
	}


	public UsersBalanceEntity(int user1, int user2, BigDecimal balance) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.balance = balance;
	}
	
	
	public long getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(long balanceId) {
		this.balanceId = balanceId;
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

	public int getUser1() {
		return user1;
	}

	public void setUser1(int user1) {
		this.user1 = user1;
	}

	public int getUser2() {
		return user2;
	}

	public void setUser2(int user2) {
		this.user2 = user2;
	}


	public BigDecimal getPendingTransactionAmount() {
		return pendingTransactionAmount;
	}


	public void setPendingTransactionAmount(BigDecimal pendingTransactionAmount) {
		this.pendingTransactionAmount = pendingTransactionAmount;
	}


	public UsersNameMapping getUsersNameMapping() {
		return usersNameMapping;
	}


	public void setUsersNameMapping(UsersNameMapping usersNameMapping) {
		this.usersNameMapping = usersNameMapping;
	}


	public boolean isTransacted() {
		return transacted;
	}


	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}


	public UsersInvitationsDetailEntity getUsersInvitationsDetailEntity() {
		return usersInvitationsDetailEntity;
	}


	public void setUsersInvitationsDetailEntity(UsersInvitationsDetailEntity usersInvitationsDetailEntity) {
		this.usersInvitationsDetailEntity = usersInvitationsDetailEntity;
	}


	/*public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}
*/
	
	
	
}
