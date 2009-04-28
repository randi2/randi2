package de.randi2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Role;
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
	@Autowired private SessionFactory sessionFactory;


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
	@Transactional(propagation=Propagation.REQUIRED)
	public void afterSaveNewDomainObject(ProceedingJoinPoint pjp)
			throws Throwable {
		System.out.println("Aspect");
		long objectId = ((AbstractDomainObject) pjp.getArgs()[0]).getId();
		pjp.proceed();
		for (Object o : pjp.getArgs()) {
			if (objectId == AbstractDomainObject.NOT_YET_SAVED_ID) {
				Login login = (Login) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				if (o instanceof Login) {
					for (Role r : ((Login) pjp.getArgs()[0]).getRoles()) {
						r = (Role) sessionFactory.getCurrentSession().createQuery("from Role where name = ?").setParameter(0, r.getName()).uniqueResult();
					}
					roleAndRigths.registerPerson(((Login) o));
				}
				roleAndRigths.grantRigths(((AbstractDomainObject) o), login
						.getPerson().getTrialSite());
			}
		}

	}


}
