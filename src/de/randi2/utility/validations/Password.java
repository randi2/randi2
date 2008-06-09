package de.randi2.utility.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;


@ValidatorClass(PasswordValidator.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Password {
	
	int min() default  8;
	int max() default 50;
	int hash_length() default 64;
	String message() default "wrong";
}
