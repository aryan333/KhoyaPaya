package com.saifintex.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.saifintex.annotation.LastNameValidate;

public class UpdateUserParams {
	
	private int userId;
	
	@Size(min = 3, max = 100)
	@Pattern(regexp = "[a-zA-z\\s]+",message = "enter validate name")
	private String firstName;
	
	@LastNameValidate(message="LastName must between 3 and 10 characters")
	private String lastName;
	
	@Email
	private String emailId;
	
	private String address1;
	
	private String gender;
	
	
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}
