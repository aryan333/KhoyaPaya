package com.saifintex.services.impl;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.saifintex.common.AbstractBase;



public class BaseService extends AbstractBase {
	
	//@Autowired BCryptPasswordEncoder passwordEncoder;
	
	public String encryptPassword(String password){
		//String encryptPassword=passwordEncoder.encode(password);
		return password;
	}
	public String  transactionId() {
		   long current= System.currentTimeMillis();
		  return("4768"+current);
		  
		}
	
	protected String getCurrentTime(){
		Date dt = new Date();
		java.text.SimpleDateFormat sdf = 
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

		String currentTime = sdf.format(dt);
		return currentTime;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
