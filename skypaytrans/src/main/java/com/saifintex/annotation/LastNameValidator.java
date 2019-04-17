package com.saifintex.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameValidator implements ConstraintValidator<LastNameValidate, String>{

	@Override
	public void initialize(LastNameValidate constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String lastName, ConstraintValidatorContext context) {
		
		return lastName == null || lastName.isEmpty() || lastName.matches("[a-zA-Z]{3,20}");
	}

}
