package com.saifintex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Relations")
@Entity
public class RelationsEntity extends BaseEntity{
	
	public RelationsEntity() {

	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RelationsId")
	private int id;
	
	@Column(name="Relation",length=15)
	private String relation;
	
	@Column(name="isActive")
	private boolean isActive;	

	@Column(name="Description")
	private String description;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
