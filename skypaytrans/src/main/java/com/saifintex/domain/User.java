package com.saifintex.domain;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saifintex.annotation.LastNameValidate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseDomain {

	private int id;

	@NotBlank
	@Pattern(regexp = "[0-9]{10}")
	private String phNumber;

	@Size(min = 4, max = 10)
	private String password;

	private String userType;

	private String inviteCode;

	@Size(min = 3, max = 30)
	@NotNull
	@NotBlank
	@Pattern(regexp = "[a-zA-z\\s]+", message = "enter valid name")
	private String firstName;

	@LastNameValidate(message = "should be characters only")
	private String lastName;

	private boolean isActive;

	private boolean isTestUser;

	private String fcmToken;

	@NotNull
	@Valid
	private UserDetail userDetail;

	private SaiCards saiCards;

	private Set<Roles> roles;

	private String pin;
	
	private boolean isPinProtected;

	private String gstinNumber;

	private String referralCode;

	private boolean notificationEnable;

	private String mappedName;

	private Set<RewardPoints> rewardPoints;

	private UsersTotalEarnedPoints usersTotalEarnedPoints;
	
	private String userCategroy;
	
	private boolean merchant;

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public boolean isPinProtected() {
		return isPinProtected;
	}

	public void setPinProtected(boolean isPinProtected) {
		this.isPinProtected = isPinProtected;
	}

	public String getPhNumber() {
		return phNumber;
	}

	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isTestUser() {
		return isTestUser;
	}

	public void setTestUser(boolean isTestUser) {
		this.isTestUser = isTestUser;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public SaiCards getSaiCards() {
		return saiCards;
	}

	public void setSaiCards(SaiCards saiCards) {
		this.saiCards = saiCards;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public String getGstinNumber() {
		return gstinNumber;
	}

	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	public boolean isNotificationEnable() {
		return notificationEnable;
	}

	public void setNotificationEnable(boolean notificationEnable) {
		this.notificationEnable = notificationEnable;
	}

	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public Set<RewardPoints> getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Set<RewardPoints> rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public UsersTotalEarnedPoints getUsersTotalEarnedPoints() {
		return usersTotalEarnedPoints;
	}

	public void setUsersTotalEarnedPoints(UsersTotalEarnedPoints usersTotalEarnedPoints) {
		this.usersTotalEarnedPoints = usersTotalEarnedPoints;
	}

	public String getUserCategroy() {
		return userCategroy;
	}

	public void setUserCategroy(String userCategroy) {
		this.userCategroy = userCategroy;
	}

	public boolean isMerchant() {
		return merchant;
	}

	public void setMerchant(boolean merchant) {
		this.merchant = merchant;
	}
	

}
