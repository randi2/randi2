package de.randi2.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;



@Aspect
public class DomainModelAspects{

	public DomainModelAspects(){
		System.out.println("ich wurde konfiguriert");
	}
	
	@Before("execution(public * de.randi2.model.*.get*(..))")
	public void beforeSetString(){
		System.out.println("ich wurde gerufen");
	}
	
	
}