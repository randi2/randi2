/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.utility.logging.LogService;
import de.randi2.utility.logging.LogEntry.ActionType;

/**
 * The Class LogAspects.
 */
@Aspect
public class LogAspects {

	/** The log service. */
	@Autowired private LogService logService;
	
	
	/**
	 * Log create new object.
	 * 
	 * @param pjp
	 *            the pjp
	 * 
	 * @throws Throwable
	 *             the throwable
	 */
	@Around("execution(public void de.randi2.services.*.create*(..))")
	public void logCreateNewObject(ProceedingJoinPoint pjp) throws Throwable{
		pjp.proceed();		
		logService.logChange(ActionType.CREATE, SecurityContextHolder.getContext().getAuthentication().getName(), ((AbstractDomainObject)pjp.getArgs()[0]));
	}
	
	
	/**
	 * Log update object.
	 * 
	 * @param pjp
	 *            the pjp
	 * 
	 * @return the object
	 * 
	 * @throws Throwable
	 *             the throwable
	 */
	@Around("execution(public * de.randi2.services.*.update*(..))")
	public Object logUpdateObject(ProceedingJoinPoint pjp) throws Throwable{
		Object o = pjp.proceed();		
		logService.logChange(ActionType.UPDATE, SecurityContextHolder.getContext().getAuthentication().getName(), ((AbstractDomainObject)pjp.getArgs()[0]));
		return o;
	}
	
	
	/**
	 * Log randomize.
	 * 
	 * @param pjp
	 *            the pjp
	 * 
	 * @return the object
	 * 
	 * @throws Throwable
	 *             the throwable
	 */
	@Around("execution(public * de.randi2.services.*.randomize*(..))")
	public Object  logRandomize(ProceedingJoinPoint pjp) throws Throwable{
		Object o = pjp.proceed();	
		logService.logRandomize(ActionType.RANDOMIZE,  SecurityContextHolder.getContext().getAuthentication().getName(), Trial.class.cast(o), ((TrialSubject)pjp.getArgs()[1]));
		return o;
	}
	
	
	/**
	 * Log login.
	 * 
	 * @param pjp
	 *            the pjp
	 * 
	 * @throws Throwable
	 *             the throwable
	 */
	@Around("execution(public * de.randi2.utility.security.DaoAuthenticationProviderWithLock.additionalAuthenticationChecks*(..))")
	public void logLogin(ProceedingJoinPoint pjp) throws Throwable{
		pjp.proceed();
		logService.logGet(ActionType.LOGIN, SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
