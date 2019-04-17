package com.saifintex.dto;

public class UserPointsEarnedDetailDTO extends BaseDto{
	
	private int pointsEarned;
	private String name;
	private String mobNumber;
	private String rewardSchemeName;
	private String rewardSchemeDesc;
	public int getPointsEarned() {
		return pointsEarned;
	}
	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobNumber() {
		return mobNumber;
	}
	public void setMobNumber(String mobNumber) {
		this.mobNumber = mobNumber;
	}
	public String getRewardSchemeName() {
		return rewardSchemeName;
	}
	public void setRewardSchemeName(String rewardSchemeName) {
		this.rewardSchemeName = rewardSchemeName;
	}
	public String getRewardSchemeDesc() {
		return rewardSchemeDesc;
	}
	public void setRewardSchemeDesc(String rewardSchemeDesc) {
		this.rewardSchemeDesc = rewardSchemeDesc;
	}
	

}
