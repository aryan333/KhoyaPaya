package com.saifintex.dto;




public class NotificationTypeDTO extends BaseDto {

	/**
	 * 
	 */
	
	private int id;
	
	
	private String notificationType;
	
	
	private String description;
	
	
	private NotificationTemplateDTO notificationTemplateDTO;

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

	public NotificationTemplateDTO getNotificationTemplateDTO() {
		return notificationTemplateDTO;
	}

	public void setNotificationTemplateDTO(NotificationTemplateDTO notificationTemplateDTO) {
		this.notificationTemplateDTO = notificationTemplateDTO;
	}

	

	
}
