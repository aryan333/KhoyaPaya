package com.javatechie.spring.orm.api.dto;

import java.util.Date;

import com.javatechie.spring.orm.api.entity.PersonDetailsEntity;

public class PersonDto {

	
	private int id;
	private String name;
	private Date dob;
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
