package com.saifintex.dto;

import java.util.Set;

public class UsersPointsMappingDTO extends BaseDto{

	private int id;	
	private int userId;	
	private int referenceId;	
	private Set<RewardPointsDTO> rewardPointsDTOs;	
	private int pointsEarned;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}	
	public Set<RewardPointsDTO> getRewardPointsDTOs() {
		return rewardPointsDTOs;
	}
	public void setRewardPointsDTOs(Set<RewardPointsDTO> rewardPointsDTOs) {
		this.rewardPointsDTOs = rewardPointsDTOs;
	}
	public int getPointsEarned() {
		return pointsEarned;
	}
	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	
}
