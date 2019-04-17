package com.saifintex.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

public class UserLogin {
	// @NotEmpty(message="Please enter your phone number ") @Size(min=10
	// ,max=10,message="please enter 10 digit mobile number")
	@NotBlank
	@Pattern(regexp="[0-9]+")
	@Size(max=10,min=10)
	private String userLogin;

	// @NotEmpty(message="Please enter your password")
	@NotBlank
	private String password;

	private String userType;
	private String latitude;
	private String longitude;
	

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
