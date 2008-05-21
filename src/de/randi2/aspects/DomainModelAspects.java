package de.randi2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DomainModelAspects {

	public DomainModelAspects() {
		System.out.println("ich wurde konfiguriert");
	}

	@Around("execution(public void de.randi2.model.*.set*(java.lang.String))")
	public void filterSetString(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("String: " + pjp.getArgs()[0]);

		String value = (pjp.getArgs()[0] != null) ? ((String) pjp.getArgs()[0])
				.trim() : "";

		pjp.proceed(new String[] { value });
	}

}