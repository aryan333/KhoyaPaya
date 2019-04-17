package com.saifintex.validators;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.saifintex.domain.Message;

public class MessageValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Message.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		@SuppressWarnings("unchecked")
		List<Message> list=(List<Message>)arg0;
		for(Message message:list){
			if(message.getTime()==null||message.getTime().equalsIgnoreCase("null")/*||!message.getTime().matches("\\d{4}-\\d{2}-\\d{2}")*/){
				errors.reject("time");
			}else if(message.getDeviceId().isEmpty() || message.getDeviceId()==null|| 
					message.getDeviceId().equalsIgnoreCase("null")){
				errors.reject("deviceId");
			}else if(message.getUserId()==0){
				errors.reject("userId");
			}
			 SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

			    try {
			         df.parse(message.getTime());
			    } catch (ParseException e) {
			        errors.reject("time");
			    }
			
		}
		

	}

}
