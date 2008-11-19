package de.randi2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.context.SecurityContextHolder;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Role2;
import de.randi2.utility.security.RolesAndRights;

/**
 * Aspect to grant automatically the rights for new AbstracDomainObject and User. 
 * 
 * @author Daniel Schrimpf <dschrimpf@users.sourceforge.net>
 *
 */
@Aspect
public class RigthAndRolesAspects {

	@Autowired
	private RolesAndRights roleAndRigths;
	@Autowired
	private HibernateTemplate template;


	/**
	 * This around advice grant the rights for an new domain object and register a new
	 * user with his special rights. 
	 * It matches all executions of save methods in the de.randi2.dao package. 
	 * 
	 * @param pjp
	 *            the proceeding join point
	 * @throws Throwable
	 */
	@Around("execution(public void de.randi2.dao.*.save*(de.randi2.model.AbstractDomainObject))")
	public void afterSaveNewDomainObject(ProceedingJoinPoint pjp)
			throws Throwable {
		long objectId = ((AbstractDomainObject) pjp.getArgs()[0]).getId();
		pjp.proceed();
		for (Object o : pjp.getArgs()) {
			if (objectId == AbstractDomainObject.NOT_YET_SAVED_ID) {
				Login login = (Login) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				if (o instanceof Login) {
					for (Role2 r : ((Login) pjp.getArgs()[0]).getRoles()) {
						r = (Role2) template.findByExample(r).get(0);
					}
					roleAndRigths.registerPerson(((Login) o));
				}
				roleAndRigths.grantRigths(((AbstractDomainObject) o), login
						.getPerson().getTrialSite());
			}
		}

	}


}
