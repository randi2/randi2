package de.randi2test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.Login;
import de.randi2.model.TrialSite;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2test.utility.DomainObjectFactory;
import de.randi2test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml", "/META-INF/subconfig/security.xml" })
public class HibernateAclServiceTest {

	@Autowired
	private HibernateAclService aclService;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private TestStringUtil testStringUtil;
	@Autowired
	private HibernateTemplate template;
	
	@Test
	public void testCreateAcl(){
		TrialSite trialsite = factory.getTrialSite();
		template.saveOrUpdate(trialsite);
		Login login = factory.getLogin();
		template.saveOrUpdate(login);
		
	    AclHibernate acl = aclService.createAcl(trialsite, login.getUsername());
		assertTrue(acl.getId()>0);

		
	}
	
	@Test
	public void testCreateAclWithPermission(){
		TrialSite trialsite = factory.getTrialSite();
		template.saveOrUpdate(trialsite);
		Login login = factory.getLogin();
		template.saveOrUpdate(login);
		
		 AclHibernate acl =  aclService.createAclwithPermissions(trialsite, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ});
		assertTrue(acl.getId()>0);
		assertEquals(1, acl.getAces().size());
	}
	
	@Test
	public void testFindAclByObjectIdentityAndSid(){
		TrialSite trialsite = factory.getTrialSite();
		template.saveOrUpdate(trialsite);
		Login login = factory.getLogin();
		template.saveOrUpdate(login);
		
		 AclHibernate acl =  aclService.createAclwithPermissions(trialsite, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ});
		assertTrue(acl.getId()>0);
		assertEquals(1, acl.getAces().size());
		Acl newAcl = aclService.readAclById(new ObjectIdentityHibernate(trialsite.getClass(),trialsite.getId()), new PrincipalSid[]{new PrincipalSid(login.getUsername())});
		assertEquals(1,newAcl.getEntries().length);
		assertEquals(PermissionHibernate.READ.getMask(), newAcl.getEntries()[0].getPermission().getMask());
	}
	
}
