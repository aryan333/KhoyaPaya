package com.javatechie.spring.orm.api.dto;

import java.util.List;

public class UserDto {
	
	private int id;
	private String name;
	private String mobile;
	private String password;
	private String emailId;
	private List<ProductFoundDTO> productFoundDTO;
	
	
	
	public List<ProductFoundDTO> getProductFoundDTO() {
		return productFoundDTO;
	}
	public void setProductFoundDTO(List<ProductFoundDTO> productFoundDTO) {
		this.productFoundDTO = productFoundDTO;
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
