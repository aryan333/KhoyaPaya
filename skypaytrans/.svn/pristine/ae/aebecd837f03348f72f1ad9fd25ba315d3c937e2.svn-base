package com.saifintex.web.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class ResetPasswordWeb {
	
	
	private String currentPassword;	
	
	@NotBlank(message="field can not be blank")
	@NotEmpty(message="field can not be empty")
	@Size(max=20,min=4)
	private String newPassword;	
	
	@NotBlank(message="field can not be blank")
	@NotEmpty(message="field can not be empty")	
	private String confPassword;
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfPassword() {
		return confPassword;
	}
	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	

}
