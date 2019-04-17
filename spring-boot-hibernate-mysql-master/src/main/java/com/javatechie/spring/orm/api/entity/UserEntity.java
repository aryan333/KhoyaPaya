package com.javatechie.spring.orm.api.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class UserEntity {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String mobile;
	private String password;
	private String emailId;
	
	@OneToMany(mappedBy="userEntity")
	private List<ProductFoundEntity> productFoundEntity;
	
	
	
	
	public List<ProductFoundEntity> getProductFoundEntity() {
		return productFoundEntity;
	}
	public void setProductFoundEntity(List<ProductFoundEntity> productFoundEntity) {
		this.productFoundEntity = productFoundEntity;
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
