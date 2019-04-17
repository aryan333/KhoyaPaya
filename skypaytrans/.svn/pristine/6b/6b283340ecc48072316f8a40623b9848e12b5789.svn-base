package com.saifintex.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.UserDAO;
import com.saifintex.services.UpdatePasswordService;


@Service("updatePasswordService")
@Transactional
public class UpdatePasswordServiceImpl extends AbstractBase implements UpdatePasswordService{
	@Autowired 
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	UserDAO userDao;
	public boolean updatePassword(String email,String password){
		int result=0;//userDao.updatePasswordByEmail(email, encryptPassword(password));
		if(result==1){
			return true;
		}
		return false;
	}
	
	public String encryptPassword(String password){
		String encryptPassword=passwordEncoder.encode(password);
		return encryptPassword;
	}
	

}
