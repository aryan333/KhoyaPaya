/*package com.saifintex.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.LoginDAO;
import com.saifintex.dto.CreateUserDTO;
import com.saifintex.dto.SigninDTO;
import com.saifintex.entity.UserEntity;
*//**
 * 
 * @author SaiFinTex
 *
 *//*
@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {

	private static int id = 0;
	@Autowired
	private SessionFactory sessionFactory;
	*//**
	 * 
	 * @param signinDTO
	 * @return
	 *//*
	public boolean signIn(SigninDTO signinDTO) {
		boolean found = false;
		List<UserEntity> users = sessionFactory.getCurrentSession().createCriteria(UserEntity.class).list();
		for(UserEntity user : users) {
			if(user.getFirstName().equals(signinDTO.getUserName()) &&
					user.getPassword().equals(signinDTO.getUserPassword())) {
				found = true;
			} 
		}
		return found;
	}

	public Integer createUser(CreateUserDTO createUserDTO) {
		UserEntity entity = copyInEntity(createUserDTO);
		Integer id = (Integer) sessionFactory.getCurrentSession().save(entity);
		return id;
	}

	private UserEntity copyInEntity(CreateUserDTO createUserDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(createUserDTO.getFirstName());
		userEntity.setLastName(createUserDTO.getLastName());
		//userEntity.setMiddleName(createUserDTO.getMiddleName());
		userEntity.setPassword(createUserDTO.getPassword());
	//	userEntity.setConfirmPassword(createUserDTO.getConfirmPassword());
	//	userEntity.setEmail(createUserDTO.getEmail());
	//	userEntity.setPhNumber(createUserDTO.getPhNumber());
	//	userEntity.setAge(createUserDTO.getAge());
		return userEntity;
	}
}
*/