package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AppPreferences")
public class AppPreferencesEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="IsShowTestUsers")
	private boolean isShowTestUsers;
	
	@Column(name="InviteBenefit")
	private boolean inviteBenefit;
	
	@Column(name="SignupBenefit")
	private boolean signupBenefit;
	
	@Column(name="MaxPointsCanBeEarned")
	private int maxPointsEarned;
	
	@Column(name="EkAppVersion")
	private String appVersion;
	@Column(name="IsTransactionBenefits")
	private boolean transactionBenefits;
	@Column(name="IsRewardOnFirstTxn")
	private boolean rewardOnFirstTxn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isShowTestUsers() {
		return isShowTestUsers;
	}

	public void setShowTestUsers(boolean isShowTestUsers) {
		this.isShowTestUsers = isShowTestUsers;
	}

	public boolean isInviteBenefit() {
		return inviteBenefit;
	}

	public void setInviteBenefit(boolean inviteBenefit) {
		this.inviteBenefit = inviteBenefit;
	}

	public boolean isSignupBenefit() {
		return signupBenefit;
	}

	public void setSignupBenefit(boolean signupBenefit) {
		this.signupBenefit = signupBenefit;
	}

	public int getMaxPointsEarned() {
		return maxPointsEarned;
	}

	public void setMaxPointsEarned(int maxPointsEarned) {
		this.maxPointsEarned = maxPointsEarned;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public boolean isTransactionBenefits() {
		return transactionBenefits;
	}

	public void setTransactionBenefits(boolean transactionBenefits) {
		this.transactionBenefits = transactionBenefits;
	}

	public boolean isRewardOnFirstTxn() {
		return rewardOnFirstTxn;
	}

	public void setRewardOnFirstTxn(boolean rewardOnFirstTxn) {
		this.rewardOnFirstTxn = rewardOnFirstTxn;
	}

}
