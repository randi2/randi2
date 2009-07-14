package de.randi2.aspects;

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

@Aspect
public class SecurityAspects {

	@Autowired
	private AclService aclService;

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
		}
		throw new AccessDeniedException("You have not the permission to randomize in this trial!");
	}

}
