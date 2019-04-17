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
@Entity
public class UsersNameMapping extends BaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="User1Name",length=30)
	private String userName1;
	
	@Column(name="User2Name",length=30)
	private String userName2;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="RelationId",nullable=true)
	private RelationsEntity relationEntity;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="OppositeRelationId",nullable=true)
	private RelationsEntity oppositeRelationEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public RelationsEntity getRelationEntity() {
		return relationEntity;
	}

	public void setRelationEntity(RelationsEntity relationEntity) {
		this.relationEntity = relationEntity;
	}

	public RelationsEntity getOppositeRelationEntity() {
		return oppositeRelationEntity;
	}

	public void setOppositeRelationEntity(RelationsEntity oppositeRelationEntity) {
		this.oppositeRelationEntity = oppositeRelationEntity;
	}
	

}
