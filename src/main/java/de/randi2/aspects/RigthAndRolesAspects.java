package de.randi2.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.utility.security.RolesAndRights;

/**
 * Aspect to grant automatically the rights for new AbstracDomainObject and
 * User.
 * 
 * @author Daniel Schrimpf <dschrimpf@users.sourceforge.net>
 * 
 */
@Aspect
public class RigthAndRolesAspects {

	private Logger logger = Logger.getLogger(RigthAndRolesAspects.class);
	@Autowired
	private RolesAndRights roleAndRigths;

	/**
	 * This around advice grant the rights for an new domain object and register
	 * a new user with his special rights. It matches all executions of save
	 * methods in the de.randi2.dao package.
	 * 
	 * @param pjp
	 *            the proceeding join point
	 * @throws Throwable
	 */
	@Around("execution(public void de.randi2.dao.*.create*(de.randi2.model.AbstractDomainObject))")
	@Transactional(propagation = Propagation.REQUIRED)
	public void afterSaveNewDomainObject(ProceedingJoinPoint pjp)
			throws Throwable {
		pjp.proceed();
		for (Object o : pjp.getArgs()) {

			Login login = (Login) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			if (o instanceof Login) {
				roleAndRigths.registerPerson(((Login) o));
			}
			logger.debug("Register Object ("+o.getClass().getSimpleName()+" id="+((AbstractDomainObject)o).getId()+")" );
			roleAndRigths.grantRigths(((AbstractDomainObject) o), login
					.getPerson().getTrialSite());
		}

	}

}
