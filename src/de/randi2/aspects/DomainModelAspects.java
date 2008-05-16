package de.randi2.aspects;



import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import de.randi2.model.Center;



@Aspect
public class DomainModelAspects {

	@Before("execution(public * de.randi2..*(..))")
	public void doSomething(){
		
	}

}
