package de.randi2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.context.SecurityContextHolder;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Role2;

@Aspect
public class TestAspect {
	
	
	@Around("execution(public * de.randi2.dao.*.getAll*(..))")
	public void afterSaveNewDomainObject(ProceedingJoinPoint pjp)
			throws Throwable {
		long time1 =System.currentTimeMillis();
		pjp.proceed();
		long time2 =System.currentTimeMillis();
		
		System.out.println("get all time in ms " + (time2-time1));

	}
}
