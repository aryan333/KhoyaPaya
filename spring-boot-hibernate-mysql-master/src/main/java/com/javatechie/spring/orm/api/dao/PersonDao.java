package com.javatechie.spring.orm.api.dao;

import java.util.List;

import com.javatechie.spring.orm.api.dto.PersonDto;
import com.javatechie.spring.orm.api.dto.ProductFoundDTO;
import com.javatechie.spring.orm.api.dto.ProductTypeDTO;
import com.javatechie.spring.orm.api.dto.StateDTO;
import com.javatechie.spring.orm.api.entity.PersonEntity;
import com.javatechie.spring.orm.api.entity.ProductFoundEntity;
import com.javatechie.spring.orm.api.entity.UserEntity;
import com.javatechie.spring.orm.api.model.Person;
import com.javatechie.spring.orm.api.model.ProductFound;
import com.javatechie.spring.orm.api.model.User;

public interface PersonDao {
	
	
	public void savePerson(PersonEntity person);
	
	public List<PersonEntity> getPersons();
	
	public void signUp(UserEntity userEntity);
	
	public void userUpdate(UserEntity userEntity,int id);
	
	public void deleteUser(int id);
	
	public List<PersonDto> getIndPerson();
	
	public UserEntity login(String mobile);
	
	public void userDemo();

	public UserEntity getUserdata(String mobile);

	public List<ProductTypeDTO> getProductType();

	public List<StateDTO> getState();

	public List<ProductFoundDTO> saveProductFound(ProductFoundEntity productFoundEntity);

	public List<ProductFoundDTO> productFoundList();
	
	
	
	

}
