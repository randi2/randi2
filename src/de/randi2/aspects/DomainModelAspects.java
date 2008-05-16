package de.randi2.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;



@Aspect
public class DomainModelAspects{

	
	@Pointcut("execution(public * set*(String)) within(de.randi2.model.*) args(java.lang.String)")
	private void stringSetPointcut(){}
	
	@Before("stringSetPointcut()")
	public void beforeStringSet(JoinPoint jp){
		System.out.println("ich wurde gerufen");
	}
	

}
