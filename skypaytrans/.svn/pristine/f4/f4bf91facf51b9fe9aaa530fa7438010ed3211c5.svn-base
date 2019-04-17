package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Notifications")
public class NotificationEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NotificationId")
	private long id; 
	
	@Column(name="NotificationTitle")
	private String notificationTitle;
	
	@Column(name="NotificationBody")
	private String notificationBody;
	
	@Column(name="SenderId")
	private int senderId;
	
	@Column(name="ReceiverId")
	private int receiverId;
	@ManyToOne
	@JoinColumn(name="NTypeId",nullable=false)
	private NotificationTypeEntity notificationTypeEntity;
	
	@ManyToOne
	@JoinColumn(name="NStatusId",nullable=false)
	private NotificationStatusEntity notificationStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public NotificationTypeEntity getNotificationTypeEntity() {
		return notificationTypeEntity;
	}

	public void setNotificationTypeEntity(NotificationTypeEntity notificationTypeEntity) {
		this.notificationTypeEntity = notificationTypeEntity;
	}

	public NotificationStatusEntity getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(NotificationStatusEntity notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	
	
	

}
