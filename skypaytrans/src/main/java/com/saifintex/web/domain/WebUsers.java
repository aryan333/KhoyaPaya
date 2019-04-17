package com.saifintex.web.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.saifintex.annotation.ValidEmail;
import com.saifintex.domain.BaseDomain;
import com.saifintex.domain.Roles;
import com.saifintex.domain.UserDetail;

public class WebUsers extends BaseDomain {

	private int webUserId;
	@NotEmpty 
	@NotNull
	private String firstName;
	
	private String lastName;
	
	private String userLogin;
	private String password;
	@Valid
	private UserDetail userDetail;
	private List<Roles> roles;
	private boolean isTestUser;
	private Date lastResetPasswordDate;
	
	public int getWebUserId() {
		return webUserId;
	}
	public void setWebUserId(int webUserId) {
		this.webUserId = webUserId;
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
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	public boolean isTestUser() {
		return isTestUser;
	}
	public void setTestUser(boolean isTestUser) {
		this.isTestUser = isTestUser;
	}
	public Date getLastResetPasswordDate() {
		return lastResetPasswordDate;
	}
	public void setLastResetPasswordDate(Date lastResetPasswordDate) {
		this.lastResetPasswordDate = lastResetPasswordDate;
	}
	
	
}
