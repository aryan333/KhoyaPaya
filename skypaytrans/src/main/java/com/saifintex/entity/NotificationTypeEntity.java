package com.saifintex.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="NotificationTypeMaster")
public class NotificationTypeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NTypeId")
	private int id;
	
	@Column(name="NotificationType")
	private String notificationType;
	
	@Column(name="Description")
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "NTemplateId", nullable = false)
	private NotificationTemplateEntity notificationTemplateEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public NotificationTemplateEntity getNotificationTemplateEntity() {
		return notificationTemplateEntity;
	}

	public void setNotificationTemplateEntity(NotificationTemplateEntity notificationTemplateEntity) {
		this.notificationTemplateEntity = notificationTemplateEntity;
	}

	
}
