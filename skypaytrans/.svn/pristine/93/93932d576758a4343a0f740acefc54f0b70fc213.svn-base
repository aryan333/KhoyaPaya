package com.saifintex.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.saifintex.entity.BaseEntity;
import com.saifintex.entity.UserEntity;

@Entity
@Table(name="PasswordResetToken")
public class PasswordResetTokenEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PasswordResetTokenId")
	private int id;
	
	@Column(name="Token",nullable=false,length=100)
	private String token;
	
	@OneToOne(targetEntity=UserEntity.class,fetch= FetchType.LAZY)
	@JoinColumn(name = "UserId")
	private UserEntity userEntity;
	
	@OneToOne(targetEntity=WebUsersEntity.class,fetch= FetchType.LAZY)
	@JoinColumn(name = "WebUserId")
	private WebUsersEntity webUsersEntity;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public WebUsersEntity getWebUsersEntity() {
		return webUsersEntity;
	}

	public void setWebUsersEntity(WebUsersEntity webUsersEntity) {
		this.webUsersEntity = webUsersEntity;
	}
	
	
}
