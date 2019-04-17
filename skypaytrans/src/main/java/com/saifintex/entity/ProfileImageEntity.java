package com.saifintex.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UsersProfile")
public class ProfileImageEntity extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public ProfileImageEntity() {
	
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ProfileID")
	private int id;
	
	@Column(name="UserID")
	private int userId;
	
	@Column(name="BlobID")
	private String blobId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getBlobId() {
		return blobId;
	}
	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}
	
	
	
}
