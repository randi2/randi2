package de.randi2.core.integration.security;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.InitializeDatabaseUtil;
import de.randi2.utility.security.RolesAndRights;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml", "/META-INF/subconfig/security.xml"})
@Transactional
public class RolesAndRightsTest {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private InitializeDatabaseUtil databaseUtil;
	@Autowired
	private HibernateAclService aclService;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private RolesAndRights rolesAndRights;
	

	@Before
	@SuppressWarnings("unchecked")
	public void init(){
		ManagedSessionContext.bind(sessionFactory.openSession());
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_ANONYMOUS);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_INVESTIGATOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_MONITOR);
		sessionFactory.getCurrentSession().saveOrUpdate(
				Role.ROLE_P_INVESTIGATOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_STATISTICAN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_USER);

		aclService.createAclwithPermissions(new Login(), Role.ROLE_ANONYMOUS
				.getName(),
				new PermissionHibernate[] { PermissionHibernate.CREATE },
				Role.ROLE_ANONYMOUS.getName());
		aclService.createAclwithPermissions(new Person(), Role.ROLE_ANONYMOUS
				.getName(),
				new PermissionHibernate[] { PermissionHibernate.CREATE },
				Role.ROLE_ANONYMOUS.getName());
		
		
		List<Role> roles = sessionFactory.getCurrentSession().createQuery("from Role").list();
		assertTrue(roles.contains(Role.ROLE_ADMIN));
		assertTrue(roles.contains(Role.ROLE_ANONYMOUS));
		assertTrue(roles.contains(Role.ROLE_INVESTIGATOR));
		assertTrue(roles.contains(Role.ROLE_MONITOR));
		assertTrue(roles.contains(Role.ROLE_P_INVESTIGATOR));
		assertTrue(roles.contains(Role.ROLE_STATISTICAN));
		assertTrue(roles.contains(Role.ROLE_USER));	
		
		List<AclHibernate> acls = sessionFactory.getCurrentSession().createQuery("select acl from AclHibernate acl, SidHibernate sid where acl.owner.sidname=?").setString(0, Role.ROLE_ANONYMOUS.getName()).list();
		boolean hasRightLogin = false;
		boolean hasRightPerson = false;
		for(AclHibernate acl: acls){
			if (acl.getObjectIdentity().getIdentifier()==AbstractDomainObject.NOT_YET_SAVED_ID){
				if(acl.getObjectIdentity().getType().equals(Login.class.getCanonicalName())){
					hasRightLogin = true;
				}else if(acl.getObjectIdentity().getType().equals(Person.class.getCanonicalName())){
					hasRightPerson = true;
				}
			}
		}
		assertTrue(hasRightLogin);
		assertTrue(hasRightPerson);
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void test(){
//		FIXME rewrite test
		assertTrue(true);
//		Login adminL = factory.getLogin();
//		adminL.addRole(Role.ROLE_ADMIN);
//		
//		sessionFactory.getCurrentSession().save(adminL);
//		
//		TrialSite trialSite = factory.getTrialSite();
//		trialSite.setContactPerson(factory.getPerson());
//		sessionFactory.getCurrentSession().save(trialSite);
//		adminL.getPerson().setTrialSite(trialSite);
//		rolesAndRights.registerPerson(adminL);
//		List<AclHibernate> acls = sessionFactory.getCurrentSession().createQuery("select acl from AclHibernate acl, SidHibernate sid, AccessControlEntryHibernate ace where (acl.owner.sidname = ?) AND ace.roleName= null AND ace.acl.id = acl.id").setString(0, adminL.getUsername()).list();
//		assertEquals(2, acls.size());
//		for(AclHibernate acl: acls){
//			if(!(acl.getObjectIdentity().getType().equals(Login.class) || acl.getObjectIdentity().getType().equals(Person.class))){
//				fail("Rigths for own User failed");
//			}
//		}
//		acls = sessionFactory.getCurrentSession().createQuery("select acl from AclHibernate acl, SidHibernate sid where (acl.owner.sidname = ?) AND acl.roleName= 'ROLE_ADMIN' group by acl").setString(0, adminL.getUsername()).list();
//		boolean rightAdminTrialSite = false;
//		boolean rightAdminTrialSiteC = false;
//		boolean rightAdminLoginC = false;
//		boolean rightAdminPersonC = false;
//		for(AclHibernate acl: acls){
//			boolean trightAdminTrialSite = acl.getObjectIdentity().getType().equals(TrialSite.class) && acl.getObjectIdentity().getIdentifier()==trialSite.getId(); 
//			boolean trightAdminTrialSiteC = acl.getObjectIdentity().getType().equals(TrialSite.class) && acl.getObjectIdentity().getIdentifier()==AbstractDomainObject.NOT_YET_SAVED_ID; 
//			boolean trightAdminLoginC = acl.getObjectIdentity().getType().equals(Login.class) && acl.getObjectIdentity().getIdentifier()==AbstractDomainObject.NOT_YET_SAVED_ID; 
//			boolean trightAdminPersonC = acl.getObjectIdentity().getType().equals(Person.class) && acl.getObjectIdentity().getIdentifier()==AbstractDomainObject.NOT_YET_SAVED_ID; 
//			
//			if(trightAdminLoginC) rightAdminLoginC =trightAdminLoginC;
//			if(trightAdminPersonC) rightAdminPersonC =trightAdminPersonC;
//			if(trightAdminTrialSiteC) rightAdminTrialSiteC =trightAdminTrialSiteC;
//			if(trightAdminTrialSite) rightAdminTrialSite =trightAdminTrialSite;
//			
//		}	 
//		
//		if(!(rightAdminLoginC || rightAdminPersonC || rightAdminTrialSite || rightAdminTrialSiteC)){
//			fail();
//		}
//		
//		rolesAndRights.grantRigths(adminL, trialSite);
//		acls = sessionFactory.getCurrentSession().createQuery("from AclHibernate").list();
//		boolean rightAdminPerson = false;
//		boolean rightAdminLogin = false;
//		for(AclHibernate acl: acls){
//			boolean trightAdminPerson = acl.getObjectIdentity().getType().equals(Person.class) && acl.getObjectIdentity().getIdentifier()==adminL.getPerson().getId(); 
//			boolean trightAdminLogin = acl.getObjectIdentity().getType().equals(Login.class) && acl.getObjectIdentity().getIdentifier()==adminL.getId();
//			if(trightAdminLogin) rightAdminLogin = true;
//			if(trightAdminPerson) rightAdminPerson = true;
//		}	 
//		if(!(rightAdminPerson || rightAdminLogin)){
//			fail("grant rights for new login object failed");
//		}
//
//		rolesAndRights.grantRigths(trialSite, trialSite);
//		acls = sessionFactory.getCurrentSession().createQuery("from AclHibernate").list();
//		boolean rightAnonymous = false;
//		rightAdminTrialSite = false;
//		for(AclHibernate acl: acls){
//			boolean trightAdminTrialSite = acl.getObjectIdentity().getType().equals(TrialSite.class) && acl.getObjectIdentity().getIdentifier()==trialSite.getId(); 
//			boolean trightAnonymous = acl.getObjectIdentity().getType().equals(TrialSite.class) && acl.getObjectIdentity().getIdentifier()==trialSite.getId(); 
//			if(trightAdminTrialSite) rightAdminTrialSite = true;
//			if(trightAnonymous) rightAnonymous = true;
//		}
//		if(!(rightAdminTrialSite || rightAnonymous)){
//			fail("grant rights for new trialSite object failed");
//		}
//		
	}
	
	
	
	
}
