package com.saifintex.validators;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.saifintex.domain.UserLogin;

public class UserLoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserLogin.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		UserLogin login=(UserLogin)arg0;
		ValidationUtils.rejectIfEmpty(errors, "phoneNo", "phoneno.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "password.required");
		/*if(!login.getPhoneNo().isEmpty()){
			if(!login.getPhoneNo().matches("^[0-9]{10}$")){
				errors.rejectValue("phoneNo", "phoneno.valid");
			}
		}*/
		if(!login.getPassword().isEmpty()){
			/*if(!login.getPassword().matches("")){
				errors.rejectValue("password", "password.valid");
			}*/
		}
		
	}

}
