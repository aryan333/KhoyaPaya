package com.saifintex.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


public class UpdatePin {
	
	private int userId;
	
	@NotNull @NotBlank
	@Size(min=4,max=4)
	private String newPin;
	
	@NotNull @NotBlank
	@Size(min=4,max=4)
	private String pin;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNewPin() {
		return newPin;
	}

	public void setNewPin(String newPin) {
		this.newPin = newPin;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	
	
	
	

}
