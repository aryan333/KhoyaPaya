package com.saifintex.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ForgotPassword {

	@Size(min=10,max=10)
	@NotBlank
	@Pattern(regexp="[0-9]+")
	private String phoneNo;
	
	@NotBlank @NotNull
	@Size(min=4,max=10)
	private String password;
	
	private String userType;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
	
	
}
