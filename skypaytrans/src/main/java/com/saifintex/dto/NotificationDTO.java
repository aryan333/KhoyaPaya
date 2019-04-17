package com.saifintex.dto;






public class NotificationDTO extends BaseDto{

	/**
	 * 
	 */
	
	private long id; 
	
	
	private String notificationTitle;
	
	
	private String notificationBody;
	
	
	private int senderId;
	
	
	private int receiverId;
	
	private NotificationTypeDTO notificationTypeDTO;
	
	
	private NotificationStatusDTO notificationStatusDTO;

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

	

	public NotificationTypeDTO getNotificationTypeDTO() {
		return notificationTypeDTO;
	}

	public void setNotificationTypeDTO(NotificationTypeDTO notificationTypeDTO) {
		this.notificationTypeDTO = notificationTypeDTO;
	}

	public NotificationStatusDTO getNotificationStatusDTO() {
		return notificationStatusDTO;
	}

	public void setNotificationStatusDTO(NotificationStatusDTO notificationStatusDTO) {
		this.notificationStatusDTO = notificationStatusDTO;
	}

	
	

}
