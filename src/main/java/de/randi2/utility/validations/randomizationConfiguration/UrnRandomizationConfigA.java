package de.randi2.utility.validations.randomizationConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;


@ValidatorClass(UrnRandomizationConfigValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UrnRandomizationConfigA {

	String message() default "{validator.urnRandomization}"; 
}
