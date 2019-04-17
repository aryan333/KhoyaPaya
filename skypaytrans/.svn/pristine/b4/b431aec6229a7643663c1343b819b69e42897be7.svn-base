package com.saifintex.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// no need as of now so delete it ..

@Entity
@Table(name="UsersPointsMapping")
public class UsersPointsMappingEntity extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UsersPointsMappingId")
	private int id;
	
	//signed up user id
	@Column(name="UserId")
	private int userId;
	
	@Column(name="ReferenceId")
	private int referenceId;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "UsersPointsMapping_RewardPoints", joinColumns = {
			@JoinColumn(name = "UsersPointsMappingId") }, inverseJoinColumns = { @JoinColumn(name = "RewardPointsId") })
	private Set<RewardPointsEntity> rewardPointsEntity;
	
	@Column(name="PointsEarned",length=5)
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

	public Set<RewardPointsEntity> getRewardPointsEntity() {
		return rewardPointsEntity;
	}

	public void setRewardPointsEntity(Set<RewardPointsEntity> rewardPointsEntity) {
		this.rewardPointsEntity = rewardPointsEntity;
	}

	public int getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}
	
	
	
}
