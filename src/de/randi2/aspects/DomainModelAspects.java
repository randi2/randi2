package de.randi2.aspects;



import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import de.randi2.model.Center;



@Aspect
public class DomainModelAspects {

	@Before("execution(* de.randi.*.*(..))")
	public void doSomething(){
		System.out.println("hallo");
		((Center) null).getCity();
	}

}
