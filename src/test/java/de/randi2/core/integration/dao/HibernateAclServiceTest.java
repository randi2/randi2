package de.randi2.core.integration.dao;

import static de.randi2.utility.security.ArrayListHelper.sidsOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.Login;
import de.randi2.model.TrialSite;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.testUtility.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml", "/META-INF/subconfig/security.xml" })
@Transactional
public class HibernateAclServiceTest extends AbstractDaoTest{

	@Autowired
	private HibernateAclService aclService;
	@Autowired
	private DomainObjectFactory factory;

	private TrialSite trialsite;
	
	@Before
	public void setUp(){
		super.setUp();
		trialsite = factory.getTrialSite();
		sessionFactory.getCurrentSession().save(trialsite.getContactPerson());
	}
	
	@Test
	public void testCreateAcl(){
		sessionFactory.getCurrentSession().saveOrUpdate(trialsite);
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().saveOrUpdate(login);
		
	    AclHibernate acl = aclService.createAcl(trialsite, login.getUsername());
		assertTrue(acl.getId()>0);

		
	}
	
	@Test
	public void testCreateAclWithPermission(){
		sessionFactory.getCurrentSession().saveOrUpdate(trialsite);
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().saveOrUpdate(login);
		
		 AclHibernate acl =  aclService.createAclwithPermissions(trialsite, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ});
		assertTrue(acl.getId()>0);
		assertEquals(1, acl.getAces().size());
	}
	
	@Test
	public void testFindAclByObjectIdentityAndSid(){
		sessionFactory.getCurrentSession().saveOrUpdate(trialsite);
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().saveOrUpdate(login);
		
		 AclHibernate acl =  aclService.createAclwithPermissions(trialsite, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ});
		assertTrue(acl.getId()>0);
		assertEquals(1, acl.getAces().size());
		Acl newAcl = aclService.readAclById(new ObjectIdentityHibernate(trialsite.getClass(),trialsite.getId()), sidsOf(new PrincipalSid(login.getUsername())));
		assertEquals(1,newAcl.getEntries().size());
		assertEquals(PermissionHibernate.READ.getMask(), newAcl.getEntries().get(0).getPermission().getMask());
	}
	
}
