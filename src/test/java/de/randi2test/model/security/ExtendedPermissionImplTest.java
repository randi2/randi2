package de.randi2test.model.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.model.TrialSite;
import de.randi2.model.security.AccessControlEntryHibernate;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.SidHibernate;
import de.randi2test.utility.DomainObjectFactory;
import de.randi2test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class ExtendedPermissionImplTest {

	@Autowired private HibernateTemplate hibernateTemplate;
	@Autowired private TestStringUtil stringUtil;
	@Autowired private ApplicationContext context; 
	@Autowired private DomainObjectFactory factory;
	
	
	@Test
	public void testMask(){
		AclHibernate acl = new AclHibernate();
		AccessControlEntryHibernate ace = new AccessControlEntryHibernate();
		TrialSite trialSite = factory.getTrialSite();
		hibernateTemplate.save(trialSite.getContactPerson());
		hibernateTemplate.save(trialSite);
		Login login = factory.getLogin();
		hibernateTemplate.save(login);
		SidHibernate sid = new SidHibernate(login.getUsername());
		hibernateTemplate.save(sid);
		acl.setOwner(sid);
		acl.setObjectIdentity(new ObjectIdentityHibernate(TrialSite.class,trialSite.getId()));
		ace.setAcl(acl);
		ace.setSid(sid);
		ace.setPermission(PermissionHibernate.ADMINISTRATION);
		hibernateTemplate.saveOrUpdate(acl);
		hibernateTemplate.saveOrUpdate(ace);

	}
	
	
	
}
