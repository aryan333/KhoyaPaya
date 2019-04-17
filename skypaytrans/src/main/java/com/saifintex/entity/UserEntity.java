package com.saifintex.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Users")
public class UserEntity extends BaseEntity {
	
	/**
	 * @author ==> Suraj :To store User data at sign up time
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserId")
	private int id;

	@Column(name = "UserLogin", length = 15, unique = true)
	private String phNumber;
	
	
	@Column(name = "StandeeId",unique = true)
	private String standeeId;

	@Column(name = "Password", length = 100)
	private String password;

	@Column(name = "UserType", length = 20)
	private String userType;

	@Column(name = "InviteCode", length = 20)
	private String inviteCode;

	@Column(name = "FirstName", length = 30)
	private String firstName;

	@Column(name = "LastName", length = 30)
	private String lastName;

	@Column(name = "isActive",nullable=true)
	private boolean isActive;

	@Column(name = "isTestUser",nullable=true)
	private boolean isTestUser;

	@Column(name = "FcmToken",nullable=true)
	private String fcmToken;	
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "UserDetailsId", nullable = false)
	private UserDetailsEntity userDetailsEntity;

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="SaiCardsId",nullable=false)
	private SaiCardsEntity saiCardEntity;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Users_Roles", joinColumns = { @JoinColumn(name = "UserId") }, inverseJoinColumns = {
			@JoinColumn(name = "RoleId") })
	private Set<RolesEntity> roles;
	
	@Column(name = "pin",nullable=true,length=100)
	private String pin;
	@Column(name = "IsPinProtected",nullable=true)
	private boolean isPinProtected;
	
	@Column(name = "GstinNumber",nullable=true,length=100)
	private String gstinNumber;
	
	@Column(name = "ReferenceCode",nullable=true,length=20)
	private String referralCode;
	
	@Column(name = "NotificationEnabled",nullable=true)
	private boolean notificationEnable;
	
	@Column(name = "IsEnabled")
	private boolean isEnabled;
	
	@Column(name = "IsAccountNonLocked")
    private boolean isAccountNonLocked;
	
	@Column(name = "IsAccountNonExpired")
    private boolean isAccountNonExpired;
	
	@Column(name = "IsCredentialsNonExpired")
    private boolean isCredentialsNonExpired;
	
	@Column(name = "LastResetPasswordDate")
	private Date lastResetPasswordDate;

	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="UsersTotalEarnedPointsId",nullable=false)
	private UsersTotalEarnedPointsEntity usersTotalEarnedPointsEntity;
	
	@Column(name="IsActualUser")
	private boolean isActualUser;
	
	@Column(name="UserCategory",length=10)
	
	private String userCategroy;

	@Column(name="QRMapDate")
	
	private Date qrMapDate;
	@Column(name="IsMerchant")
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
	
	public UserDetailsEntity getUserDetailsEntity() {
		return userDetailsEntity;
	}

	
	public void setUserDetailsEntity(UserDetailsEntity userDetailsEntity) {
		this.userDetailsEntity = userDetailsEntity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
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

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public SaiCardsEntity getSaiCardEntity() {
		return saiCardEntity;
	}

	public void setSaiCardEntity(SaiCardsEntity saiCardEntity) {
		this.saiCardEntity = saiCardEntity;
	}
	
	public Set<RolesEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolesEntity> roles) {
		this.roles = roles;
	}

	public String getGstinNumber() {
		return gstinNumber;
	}

	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}


	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isNotificationEnable() {
		return notificationEnable;
	}

	public void setNotificationEnable(boolean notificationEnable) {
		this.notificationEnable = notificationEnable;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public Date getLastResetPasswordDate() {
		return lastResetPasswordDate;
	}

	public void setLastResetPasswordDate(Date lastResetPasswordDate) {
		this.lastResetPasswordDate = lastResetPasswordDate;
	}

	public boolean isTestUser() {
		return isTestUser;
	}

	public void setTestUser(boolean isTestUser) {
		this.isTestUser = isTestUser;
	}
	
	public UsersTotalEarnedPointsEntity getUsersTotalEarnedPointsEntity() {
		return usersTotalEarnedPointsEntity;
	}

	public void setUsersTotalEarnedPointsEntity(UsersTotalEarnedPointsEntity usersTotalEarnedPointsEntity) {
		this.usersTotalEarnedPointsEntity = usersTotalEarnedPointsEntity;
	}

	public boolean isActualUser() {
		return isActualUser;
	}

	public void setActualUser(boolean isActualUser) {
		this.isActualUser = isActualUser;
	}

	public String getStandeeId() {
		return standeeId;
	}

	public void setStandeeId(String standeeId) {
		this.standeeId = standeeId;
	}

	public String getUserCategroy() {
		return userCategroy;
	}

	public void setUserCategroy(String userCategroy) {
		this.userCategroy = userCategroy;
	}

	public Date getQrMapDate() {
		return qrMapDate;
	}

	public void setQrMapDate(Date qrMapDate) {
		this.qrMapDate = qrMapDate;
	}

	public boolean isMerchant() {
		return merchant;
	}

	public void setMerchant(boolean merchant) {
		this.merchant = merchant;
	}
	
	

}
