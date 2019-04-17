package com.saifintex.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saifintex.annotation.LastNameValidate;
import com.saifintex.common.Number2StringDesrializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWithOpponentUserBalance extends BaseDomain {

	private static final long serialVersionUID = 1L;

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
	@Pattern(regexp = "[a-zA-z\\s]+", message = "enter validate name")
	private String firstName;

	@LastNameValidate(message = "should be characters only")
	private String lastName;

	/* private int roleId; */

	private boolean active;

	private boolean testUser;

	private String fcmToken;

	@NotNull
	@Valid
	private UserDetail userDetail;

	private SaiCards saiCards;

	private Set<Roles> roles;

	private String pin;
	private boolean isPinProtected;

	private String gstinNumber;

	private BigDecimal mutualBalance;

	private boolean mapped;
	private String mappedName;
	public String getBlobId() {
		return blobId;
	}

	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}

	private String blobId;
	
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
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isTestUser() {
		return testUser;
	}

	public void setTestUser(boolean testUser) {
		this.testUser = testUser;
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

	public String getGstinNumber() {
		return gstinNumber;
	}

	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	@JsonSerialize(using = Number2StringDesrializer.class)
	public BigDecimal getMutualBalance() {
		return mutualBalance;
	}

	public void setMutualBalance(BigDecimal mutualBalance) {
		this.mutualBalance = mutualBalance;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public boolean isMapped() {
		return mapped;
	}

	public void setMapped(boolean mapped) {
		this.mapped = mapped;
	}

	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

}
