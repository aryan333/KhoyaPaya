package com.javatechie.spring.orm.api.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javatechie.spring.orm.api.dao.PersonDao;
import com.javatechie.spring.orm.api.dto.PersonDto;
import com.javatechie.spring.orm.api.dto.ProductFoundDTO;
import com.javatechie.spring.orm.api.dto.ProductTypeDTO;
import com.javatechie.spring.orm.api.dto.StateDTO;
import com.javatechie.spring.orm.api.dto.UserDto;
import com.javatechie.spring.orm.api.entity.PersonEntity;
import com.javatechie.spring.orm.api.entity.ProductFoundEntity;
import com.javatechie.spring.orm.api.entity.ProductTypeEntity;
import com.javatechie.spring.orm.api.entity.StateEntity;
import com.javatechie.spring.orm.api.entity.UserDemo;
import com.javatechie.spring.orm.api.entity.UserEntity;
import com.javatechie.spring.orm.api.model.Person;
import com.javatechie.spring.orm.api.model.ProductFound;
import com.javatechie.spring.orm.api.model.ProductType;
import com.javatechie.spring.orm.api.model.State;
import com.javatechie.spring.orm.api.model.User;
import com.javatechie.spring.orm.api.service.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService  {
    
	@Autowired
	private PersonDao dao;
	
	@Autowired
	PasswordEncoder passencode;
	
	@Override
	public void savePerson(PersonDto personDto) {
		
		PersonEntity personEntity=new PersonEntity();
		BeanUtils.copyProperties(personDto, personEntity);
		dao.savePerson(personEntity);
		
		
	}

	@Override
	public List<PersonDto> getPersons() {
		
		
		List<PersonEntity> list=dao.getPersons();
		
		List<PersonDto> personDto=new ArrayList<PersonDto>();
		
		for(PersonEntity dto:list) {
		
			PersonDto pdto=new PersonDto();
		    BeanUtils.copyProperties(dto, pdto);
		    personDto.add(pdto);
		}
		
		
		return personDto ;
	}

	@Override
	public void signUp(UserDto userDto) {
		
		String encrptpassword=passencode.encode(userDto.getPassword());
		userDto.setPassword(encrptpassword);
		
		UserEntity userEntity=new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		dao.signUp(userEntity);
		
	}
	
	@Override
	public void userUpdate(UserDto userdto,int id) {
		
		UserEntity userEntity=new UserEntity();
		BeanUtils.copyProperties(userdto, userEntity);
		dao.userUpdate(userEntity,id);
		
	}
	
	@Override
	public void deleteUser(int id) {
		
		dao.deleteUser(id);
	}

	@Override
	public List<PersonDto> getIndPerson() {
		
		List<PersonDto> dto=dao.getIndPerson();
		System.out.println("in service"+dto.size());
		return dto;
	}

	@Override
	public String login(String mobile, String password) {
		
		
		UserEntity userEntity=dao.login(mobile);
		if(passencode.matches(password, userEntity.getPassword())) {
			return "success";
		}
		
		return "failure";
	}
	
	@Override
	//@Scheduled(cron="*/30 * * * * *")
	public void userDemo() {
		
		dao.userDemo();
	}

	@Override
	public UserDto getUserData(String mobile) {
		
		UserDto dto=new UserDto();
		UserEntity user1=dao.getUserdata(mobile);
		BeanUtils.copyProperties(user1,dto);
		return dto;
	}

	@Override
	public List<ProductTypeDTO> getProductType() {
		
		List<ProductTypeDTO> productTypedto=dao.getProductType();
		return productTypedto;
	}

	@Override
	public List<StateDTO> getState() {
		
		List<StateDTO> statedto=dao.getState();
		return statedto;
	}

	@Override
	public List<ProductFoundDTO> saveProductFound(ProductFoundDTO productFoundDTO) {
		
		ProductFoundEntity productFoundEntity=new ProductFoundEntity();
		ProductTypeEntity productTypeEntity=new ProductTypeEntity();
		StateEntity stateEntity=new StateEntity();
		UserEntity userEntity=new UserEntity();
		
		StateDTO stateDTO=productFoundDTO.getStateDTO();
		BeanUtils.copyProperties(stateDTO, stateEntity);
		
		UserDto userDto=productFoundDTO.getUserDto();
		BeanUtils.copyProperties(userDto, userEntity);
		
		ProductTypeDTO productTypeDTO=productFoundDTO.getProductTypeDTO();
		BeanUtils.copyProperties(productTypeDTO, productTypeEntity);
	
		
		BeanUtils.copyProperties(productFoundDTO, productFoundEntity);
		productFoundEntity.setProductTypeEntity(productTypeEntity);
		productFoundEntity.setStateEntity(stateEntity);
		productFoundEntity.setUserEntity(userEntity);
		
		List<ProductFoundDTO> productFounddto=dao.saveProductFound(productFoundEntity);
		
		
		return null;
	}

	@Override
	public List<ProductFound> productFoundList() {
		
		List<ProductFoundDTO> productFoundDTOs=dao.productFoundList();
		
		List<ProductFound> foundlist=new ArrayList<>();
		for(ProductFoundDTO pdto:productFoundDTOs){
			ProductFound Found=new ProductFound();
			
			StateDTO stateDTO=pdto.getStateDTO();
			State state=new State();
			BeanUtils.copyProperties(stateDTO, state);
			
			ProductTypeDTO productTypeDTO=pdto.getProductTypeDTO();
			ProductType productType=new ProductType();
			BeanUtils.copyProperties(productTypeDTO, productType);
			
			UserDto userDto=pdto.getUserDto();
			userDto.setPassword("0000");
			User user=new User();
			BeanUtils.copyProperties(userDto, user);
			
			BeanUtils.copyProperties(pdto, Found);
			Found.setState(state);
			Found.setProductType(productType);
			Found.setUser(user);
			foundlist.add(Found);
		
		}
		return foundlist;
	}

	

	

}
