package com.saifintex.annotation;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy= {NarrationSizeValidator.class})
public @interface NarrationSize {

	String message() default "{javax.validation.constraints.NotNull.message}";

	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
