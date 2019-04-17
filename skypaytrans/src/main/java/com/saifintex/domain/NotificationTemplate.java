package com.saifintex.domain;




public class NotificationTemplate extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	/**
	 * 
	 */
	
	private int id;

	
	
	
	private String templateTitle;
	
	
	private String templateBody;
	
	
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public String getTemplateBody() {
		return templateBody;
	}

	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
