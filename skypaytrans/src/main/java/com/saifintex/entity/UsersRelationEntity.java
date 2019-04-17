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
@Table(name="UsersRelation")
public class UsersRelationEntity extends BaseEntity {

	public UsersRelationEntity() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name="UsersRelationId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="User1")
	private int user1;
	
	@Column(name="User2")
	private int user2;
	
	/*@Column(name="RelationId")
	private int relationId;
	
	@Column(name="OppositeRelationId")
	private int oppositeRelationId;*/
	
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
