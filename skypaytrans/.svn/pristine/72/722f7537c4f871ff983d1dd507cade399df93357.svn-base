package com.saifintex.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class NarrationSizeValidator implements ConstraintValidator<NarrationSize, String> {

	@Override
	public void initialize(NarrationSize arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String narration, ConstraintValidatorContext context) {
		
		if(narration!=null & !narration.isEmpty()) {
			if(narration.length()>255) {
				return false;
			}
		}
		return true;
	}

}
