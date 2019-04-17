package com.saifintex.domain;


import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class UserProfileParams extends BaseDomain {

	@NotNull
	private CommonsMultipartFile file;
	
	private int userId;

	private String blobId;
	
	
	public String getBlobId() {
		return blobId;
	}

	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

}
