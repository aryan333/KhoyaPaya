package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UsersContact")
public class UsersContactsEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	
	@Column(name="UserId")
	private int loggedInUserId;
	
	@Column(name="ContactNumber",unique=true)
	private String contactNumber;
	
	@Column(name="ContactName")
	private String contactName;
	
	@Column(name="IsInvited")
	private boolean invited;
	
	@Column(name="SMSResponse")
	private String smsResponse;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getLoggedInUserId() {
		return loggedInUserId;
	}
	public void setLoggedInUserId(int loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public boolean isInvited() {
		return invited;
	}
	public void setInvited(boolean invited) {
		this.invited = invited;
	}
	public String getSmsResponse() {
		return smsResponse;
	}
	public void setSmsResponse(String smsResponse) {
		this.smsResponse = smsResponse;
	}
	
	
	
}
