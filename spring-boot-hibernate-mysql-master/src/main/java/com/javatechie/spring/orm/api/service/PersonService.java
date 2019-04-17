package com.javatechie.spring.orm.api.service;

import java.util.List;

import com.javatechie.spring.orm.api.dto.PersonDto;
import com.javatechie.spring.orm.api.dto.ProductFoundDTO;
import com.javatechie.spring.orm.api.dto.ProductTypeDTO;
import com.javatechie.spring.orm.api.dto.StateDTO;
import com.javatechie.spring.orm.api.dto.UserDto;
import com.javatechie.spring.orm.api.model.Person;
import com.javatechie.spring.orm.api.model.ProductFound;
import com.javatechie.spring.orm.api.model.User;

public interface PersonService {
	
    public void savePerson(PersonDto personDto);
	
	public List<PersonDto> getPersons();
	
	public void signUp(UserDto userDto);
	
	public void userUpdate(UserDto userdto,int id);
	
	public void deleteUser(int id);
	
	public List<PersonDto> getIndPerson();
	
	public String login(String mobile,String password);
	
	public  void userDemo();

	public UserDto getUserData(String mobile);

	public List<ProductTypeDTO> getProductType();

	public List<StateDTO> getState();

	public List<ProductFoundDTO> saveProductFound(ProductFoundDTO productFoundDTO);

	public List<ProductFound> productFoundList();
	
	
	

}
