package com.saifintex.domain;


public class UpdateRelationParams {

	public UpdateRelationParams() {
		// TODO Auto-generated constructor stub
	}	
	private int relationId;
	private int loggedInUser;  
	private int opponentUser;  		
	
	public int getLoggedInUser() {
		return loggedInUser;
	}
	public void setLoggedInUser(int loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	public int getOpponentUser() {
		return opponentUser;
	}
	public void setOpponentUser(int opponentUser) {
		this.opponentUser = opponentUser;
	}
	public int getRelationId() {
		return relationId;
	}
	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}
	
}
