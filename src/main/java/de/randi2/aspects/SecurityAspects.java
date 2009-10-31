/* 
 * (c) 2008-2009 RANDI2 Core Development Team
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

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.AclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;

/**
 * The Class SecurityAspects.
 */
@Aspect
public class SecurityAspects {

	/** The acl service. */
	@Autowired
	private AclService aclService;
	
	/** The logger. */
	private Logger logger =  Logger.getLogger(SecurityAspects.class);

	/**
	 * Aroung Aspect to secure the randomize prozess.
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
	@Transactional(propagation = Propagation.REQUIRED)
	public Object secRandomize(ProceedingJoinPoint pjp) throws Throwable {
		boolean allowedReadTrial = false;
		Trial trial = (Trial) pjp.getArgs()[0];
		TrialSubject subject = (TrialSubject) pjp.getArgs()[1];

		try {
			Acl acl = aclService.readAclById(new ObjectIdentityHibernate(
					Trial.class, trial.getId()), new Sid[] { new PrincipalSid(
					SecurityContextHolder.getContext().getAuthentication()) });
			allowedReadTrial = acl.isGranted(new PermissionHibernate[] {
					PermissionHibernate.READ,
					PermissionHibernate.ADMINISTRATION },
					new Sid[] { new PrincipalSid(SecurityContextHolder
							.getContext().getAuthentication()) }, false);
			
			if(allowedReadTrial){
				 acl = aclService.readAclById(new ObjectIdentityHibernate(
						TrialSubject.class, subject.getId()), new Sid[] { new PrincipalSid(
						SecurityContextHolder.getContext().getAuthentication()) });
				boolean allowedRandomize = acl.isGranted(new PermissionHibernate[] {
						PermissionHibernate.CREATE,
						PermissionHibernate.ADMINISTRATION },
						new Sid[] { new PrincipalSid(SecurityContextHolder
								.getContext().getAuthentication()) }, false);
				if(allowedRandomize){
					return pjp.proceed();
				}
				
			}
		} catch (NotFoundException e) {
			logger.info("The user ("+ SecurityContextHolder.getContext().getAuthentication().getName()  +")have no permission to randomize in this trial!");
		}
		throw new AccessDeniedException("You have not the permission to randomize in this trial!");
	}

}
