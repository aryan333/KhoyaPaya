package com.javatechie.spring.orm.api.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="Person")
public class PersonEntity {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private Date dob;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private PersonDetailsEntity personDetailsEntity;
	
	public PersonDetailsEntity getPersonDetailsEntity() {
		return personDetailsEntity;
	}
	public void setPersonDetailsEntity(PersonDetailsEntity personDetailsEntity) {
		this.personDetailsEntity = personDetailsEntity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}

}
