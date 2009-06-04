package de.randi2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.utility.logging.LogService;

@Aspect
public class LogAspects {

	@Autowired private LogService logService;
	
	
	@Around("execution(public void de.randi2.services.*.create*(..))")
	@Transactional(propagation = Propagation.REQUIRED)
	public void logCreateNewObject(ProceedingJoinPoint pjp) throws Throwable{
		pjp.proceed();		
		logService.logChange(pjp.getSignature().toShortString(), SecurityContextHolder.getContext().getAuthentication().getName(), ((AbstractDomainObject)pjp.getArgs()[0]));
	}
	
	
	@Around("execution(public * de.randi2.services.*.update*(..))")
	@Transactional(propagation = Propagation.REQUIRED)
	public Object logUpdateObject(ProceedingJoinPoint pjp) throws Throwable{
		Object o = pjp.proceed();		
		logService.logChange(pjp.getSignature().toShortString(), SecurityContextHolder.getContext().getAuthentication().getName(), ((AbstractDomainObject)pjp.getArgs()[0]));
		return o;
	}
	
	@Around("execution(public * de.randi2.services.*.get*(..))")
	@Transactional(propagation = Propagation.REQUIRED)
	public Object  logGet(ProceedingJoinPoint pjp) throws Throwable{
		Object o = pjp.proceed();	
		if(o instanceof AbstractDomainObject){
			logService.logChange(pjp.getSignature().getName(),  SecurityContextHolder.getContext().getAuthentication().getName(), ((AbstractDomainObject)o));
		}else logService.logGet(pjp.getSignature().getName(), SecurityContextHolder.getContext().getAuthentication().getName());
		return o;
	}
	

}
