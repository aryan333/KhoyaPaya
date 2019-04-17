package com.javatechie.spring.orm.api.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
	
	private int id;
	
	/*@NotNull
    @Size(min=4, max=30, message = "The name must be {min} to {max} characters in length.")*/
	private String name;
	@NotNull
	@Size(min=10, max=10, message = "The mobile number must be {min} digits ")
	private String mobile;
	@NotNull
    @Size(min=4, max=30, message = "The password must be {min} to {max} characters in length.")
	private String password;
	private String emailId;
	private List<ProductFound> productFound;
	
	
	public List<ProductFound> getProductFound() {
		return productFound;
	}
	public void setProductFound(List<ProductFound> productFound) {
		this.productFound = productFound;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	

}
