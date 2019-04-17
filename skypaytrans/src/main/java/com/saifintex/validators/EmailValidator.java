package com.saifintex.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.saifintex.annotation.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String>{

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
	
	
	
	@Override
	public void initialize(ValidEmail validEmail) {
	
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext arg1) {
		return checkEmailPattern(email);
	}

	private boolean checkEmailPattern(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		System.out.println("email pattern == "+matcher.matches());
		return matcher.matches();
	}
}
