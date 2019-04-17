package com.javatechie.spring.orm.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.orm.api.dto.PersonDto;
import com.javatechie.spring.orm.api.dto.UserDto;
import com.javatechie.spring.orm.api.model.Person;
import com.javatechie.spring.orm.api.model.User;
import com.javatechie.spring.orm.api.service.PersonService;

@RestController
@RequestMapping("/spring-boot-orm")
public class PersonController {

	@Autowired
	private PersonService service;

	@PostMapping("/savePerson")
	public String save(@RequestBody Person person) {
		
		
		//System.out.println("person object  "+person);
		
		PersonDto personDto=new PersonDto();
		BeanUtils.copyProperties(person, personDto);
		System.out.println(person);
		service.savePerson(personDto);
		
		
		return "success";
	}
	
	@PostMapping("/register")
	public String signUp(@RequestBody User user) {
		
		System.out.println("come in");
		UserDto userDto=new UserDto();
		BeanUtils.copyProperties(user, userDto);
		service.signUp(userDto);
		
		return "success";
	}
	
	
	@PostMapping("login")
	public String login(@RequestBody User user) {
		
		String str=service.login(user.getMobile(),user.getPassword());
		
		return str;
	}
	
	
	@PutMapping("/updateUser/{id}")
	public String updateUser(@PathVariable int id, @RequestBody User user) {
		
		UserDto userdto=new UserDto();
		BeanUtils.copyProperties(user,userdto);
		service.userUpdate(userdto,id);
		return "success";
	}
	
   @DeleteMapping("/deleteUser/{id}")
   public String deleteUser(@PathVariable int id) {
	   
	   service.deleteUser(id);
	   return "success";
   }

	@GetMapping("/getAll")
	public List<Person> getALlPersons() {
		
		List<PersonDto> list=service.getPersons();
		
		List<Person> person=new ArrayList<>();
		
		for(PersonDto dto:list) {
			
			Person person2=new Person();
			BeanUtils.copyProperties(dto, person2);
			person.add(person2);			
		}
		
		return person;
	}
	
	@GetMapping("/getInd")
	public Map< String, List<Person>> getIndPerson(){
		
		List<PersonDto> personDtos=service.getIndPerson();
		
		Map<String, List<Person>> response=new HashMap<String,List<Person>>();
		List<Person> person=new ArrayList<>();
		for(PersonDto dto:personDtos) {
		
				
		Person p=new Person();	
		BeanUtils.copyProperties(dto, p);
		person.add(p);
		}
		System.out.println(personDtos.size());	
		response.put("data", person);
		return response;
	}

}
