package com.javatechie.spring.orm.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javatechie.spring.orm.api.entity.PersonDetailsEntity;




public class Person {
	
	private int id;
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyyy")
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
