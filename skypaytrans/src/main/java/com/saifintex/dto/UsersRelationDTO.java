package com.saifintex.dto;

public class UsersRelationDTO extends BaseDto{

	public UsersRelationDTO() {
		// TODO Auto-generated constructor stub
	}

	private int id;
	private int user1;
	private int user2;
	private RelationsDTO relationDto;
	private RelationsDTO oppositeRelationDto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser1() {
		return user1;
	}
	public void setUser1(int user1) {
		this.user1 = user1;
	}
	public int getUser2() {
		return user2;
	}
	public void setUser2(int user2) {
		this.user2 = user2;
	}
	public RelationsDTO getRelationDto() {
		return relationDto;
	}
	public void setRelationDto(RelationsDTO relationDto) {
		this.relationDto = relationDto;
	}
	public RelationsDTO getOppositeRelationDto() {
		return oppositeRelationDto;
	}
	public void setOppositeRelationDto(RelationsDTO oppositeRelationDto) {
		this.oppositeRelationDto = oppositeRelationDto;
	}
	

	
}
