package de.randi2.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class DomainModelAspects{

	public DomainModelAspects(){
		System.out.println("ich wurde konfiguriert");
	}
	
	@Before("execution(* *.getFax())")
	public void onGetFax(){
		System.out.println("ich wurde gerufen");
	}
	
	
}