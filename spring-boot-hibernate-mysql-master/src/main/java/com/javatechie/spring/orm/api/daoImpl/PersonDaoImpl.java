package com.javatechie.spring.orm.api.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import com.javatechie.spring.orm.api.dao.PersonDao;
import com.javatechie.spring.orm.api.dto.PersonDto;
import com.javatechie.spring.orm.api.dto.ProductFoundDTO;
import com.javatechie.spring.orm.api.dto.ProductTypeDTO;
import com.javatechie.spring.orm.api.dto.StateDTO;
import com.javatechie.spring.orm.api.dto.UserDto;
import com.javatechie.spring.orm.api.entity.PersonDetailsEntity;
import com.javatechie.spring.orm.api.entity.PersonEntity;
import com.javatechie.spring.orm.api.entity.ProductFoundEntity;
import com.javatechie.spring.orm.api.entity.ProductTypeEntity;
import com.javatechie.spring.orm.api.entity.StateEntity;
import com.javatechie.spring.orm.api.entity.UserDemo;
import com.javatechie.spring.orm.api.entity.UserEntity;
import com.javatechie.spring.orm.api.model.ProductFound;
import com.javatechie.spring.orm.api.model.User;


@Repository
public class PersonDaoImpl implements PersonDao {
	
	
	@Autowired
	private SessionFactory factory;
	
	

	public void savePerson(PersonEntity person) {
		
		getSession().save(person);
	}

	@SuppressWarnings("unchecked")
	public List<PersonEntity> getPersons() {
		
		
		
		List<PersonEntity> personEntities=getSession().createCriteria(PersonEntity.class).list();
		return personEntities; 
	}
	
	@Override
	public void signUp(UserEntity userEntity) {
		
		getSession().save(userEntity);
		
	}
	
	@Override
	public void userUpdate(UserEntity userEntity,int id) {
		
		UserEntity user1= (UserEntity) getSession().get(UserEntity.class, id);
		user1.setEmailId(userEntity.getEmailId());
		
	}
	
	
	@Override
	public void deleteUser(int id) {
		
		UserEntity user=getSession().get(UserEntity.class, id);
		getSession().delete(user);
		
	}
	
	@Override
	public List<PersonDto> getIndPerson() {
		
		String hql="select p.name as name from PersonEntity p ";
		//StringBuilder stringBuilder=new StringBuilder("");
		Query query=getSession().createQuery(hql);
		
		System.out.println(query);
		
		List<PersonDto> dto=query.setResultTransformer(Transformers.aliasToBean(PersonDto.class)).list();
		
		System.out.println("in dao"+dto.size());
		 return dto;
	}

	private Session getSession() {
		Session session = factory.getCurrentSession();
		if (session == null) {
			session = factory.openSession();
		}
		return session;
	}

	@Override
	public UserEntity login(String mobile) {
		
		UserEntity userEntity=(UserEntity) getSession().createCriteria(UserEntity.class).add(Restrictions.eq("mobile", mobile)).uniqueResult();
		return userEntity;
	}
	
	
	@Override
	public void userDemo() {
		
		Session session = factory.getCurrentSession();
		List<UserEntity> userEntities=session.createCriteria(UserEntity.class).list();
		
		//UserDemo demo=new UserDemo();
		for(UserEntity user:userEntities) {
			
			UserDemo demo=new UserDemo();
			demo.setId(user.getId());
			demo.setEmailId(user.getEmailId());
			demo.setMobile(user.getMobile());
			demo.setPassword(user.getPassword());
			session.save(demo);
			
		}
	}

	@Override
	public UserEntity getUserdata(String mobile) {
		
		Session session=factory.getCurrentSession();
		UserEntity user1= (UserEntity) session.createCriteria(UserEntity.class).add(Restrictions.eq("mobile", mobile)).uniqueResult();
		return user1;
	}

	@Override
	public List<ProductTypeDTO> getProductType() {
		
		List<ProductTypeDTO> pdto=new ArrayList<ProductTypeDTO>();
		Session session=factory.getCurrentSession();
		List<ProductTypeEntity> productTypeEntity=(List<ProductTypeEntity>) session.createCriteria(ProductTypeEntity.class).list();
		
		for(ProductTypeEntity entity:productTypeEntity){
			ProductTypeDTO productTypeDTO=new ProductTypeDTO();
		    BeanUtils.copyProperties(entity, productTypeDTO);
		    System.out.println(productTypeDTO.getProducttype());
		    pdto.add(productTypeDTO);
		}
		
		
		return pdto;
	}

	@Override
	public List<StateDTO> getState() {
		
		List<StateDTO> sdto=new ArrayList<>();
		Session session=factory.getCurrentSession();
		List<StateEntity> stateEntity=(List<StateEntity>) session.createCriteria(StateEntity.class).list();
		
		for(StateEntity entity:stateEntity){
			
			StateDTO stateDTO=new StateDTO();
		    BeanUtils.copyProperties(entity, stateDTO);
		    System.out.println(stateDTO.getStateName());
		    sdto.add(stateDTO);
		    
		}
		
		return sdto;
	}

	@Override
	public List<ProductFoundDTO> saveProductFound(ProductFoundEntity productFoundEntity) {
		
		Session session=factory.getCurrentSession();
	    session.save(productFoundEntity);
		
		return null;
	}

	@Override
	public List<ProductFoundDTO> productFoundList() {
		
		Session session=factory.getCurrentSession();
		
		List<ProductFoundDTO> pdto=new ArrayList<ProductFoundDTO>();
		List<ProductFoundEntity> productFoundEntities=session.createCriteria(ProductFoundEntity.class).list();
		 
		
		for(ProductFoundEntity pEntity:productFoundEntities){
			ProductFoundDTO FoundDTO=new ProductFoundDTO();
			
			StateEntity stateEntity=pEntity.getStateEntity();
			StateDTO stateDTO=new StateDTO();
			BeanUtils.copyProperties(stateEntity, stateDTO);
			
			ProductTypeEntity productTypeEntity=pEntity.getProductTypeEntity();
			ProductTypeDTO productTypeDTO=new ProductTypeDTO();
			BeanUtils.copyProperties(productTypeEntity, productTypeDTO);
			
			UserEntity userEntity=pEntity.getUserEntity();
			UserDto userDto=new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			
			BeanUtils.copyProperties(pEntity, FoundDTO);
			FoundDTO.setStateDTO(stateDTO);
			FoundDTO.setProductTypeDTO(productTypeDTO);
			FoundDTO.setUserDto(userDto);
			pdto.add(FoundDTO);
		
		}
		return pdto;
	}
	
	

	

	

}
