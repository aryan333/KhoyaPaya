package com.saifintex.validators;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.saifintex.domain.User;

public class UserSignupValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		User user=(User)arg0;
		ValidationUtils.rejectIfEmpty(errors, "firstName", "firstname.required");
		ValidationUtils.rejectIfEmpty(errors, "email", "email.required");
		ValidationUtils.rejectIfEmpty(errors, "aadhaarNumber", "aadhaar.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "password.required");
		ValidationUtils.rejectIfEmpty(errors, "phoneNo", "phoneno.required");
		if(!user.getFirstName().isEmpty()){
			if(!user.getFirstName().matches("^[a-zA-Z]{3,15}$")){
				errors.rejectValue("firstName", "firstname.valid");
			}
		}
		if(!user.getLastName().isEmpty()){
			if(user.getLastName().length()<3 || user.getLastName().length()>15){
				errors.rejectValue("lastName", "lastname.valid");
			}
		}
	/*	if(!user.getAadhaarNumber().isEmpty()){
			if(!user.getAadhaarNumber().matches("^[0-9]{12}$")){
				errors.rejectValue("aadhaarNumber", "aadhaar.valid");
			}
		}
		if(!user.getAddress1().isEmpty()){
			if(user.getAddress1().length()>250){
				errors.rejectValue("address1", "address1.valid");
			}
		}
		if(!user.getPhoneNo().isEmpty()){
			if(!user.getPhoneNo().matches("^[0-9]{10}$")){
				errors.rejectValue("phoneNo", "phoneno.valid");
			}
		}
		if(!user.getEmail().isEmpty()){
			if(!user.getEmail().matches("^(.+)@(.+)$")){
				errors.rejectValue("email", "email.valid");
			}
		}
		if(!user.getPassword().isEmpty()){
				if(!user.getPassword().matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,10}$")){
					errors.rejectValue("password", "password.valid");
				}
		}*/

	}

}
