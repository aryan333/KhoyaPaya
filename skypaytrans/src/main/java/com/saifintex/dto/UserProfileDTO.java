package com.saifintex.dto;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserProfileDTO extends BaseDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
