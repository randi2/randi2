package de.randi2.core.unit.model.security;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.security.AccessControlEntryHibernate;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.SidHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class AccessControlEntryHibernateTest {

	
	@Autowired
	private SessionFactory sessionFactory;
	private AccessControlEntryHibernate ace;
	
	
	@Before
	public void setUp(){
		ace = new AccessControlEntryHibernate ();
	}
	
	
	@Test
	public void testPermission(){
		PermissionHibernate permission = PermissionHibernate.READ;
		ace.setPermission(permission);
		assertEquals(PermissionHibernate.READ, ace.getPermission());
	}
	
	@Test
	public void testAcl(){
		AclHibernate acl = new AclHibernate();
		acl.setId(1);
		ace.setAcl(acl);
		assertEquals(acl.getId(), ((AclHibernate)ace.getAcl()).getId());
	}
	
	@Test
	public void testRolename(){
		ace.setRoleName("Admin");
		assertEquals("Admin" ,ace.getRoleName());
	}
	
	@Test
	public void testSid(){
		SidHibernate sid = new SidHibernate();
		sid.setSidname("name");
		ace.setSid(sid);
		assertEquals("name", ((SidHibernate)ace.getSid()).getSidname());
	}
	
	@Test
	public void testGranting(){
		assertTrue(ace.isGranting());
		ace.setGranting(false);
		assertFalse(ace.isGranting());
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		ace.setGranting(false);
		ace.setRoleName("Admin");
		ace.setPermission(PermissionHibernate.READ);
		sessionFactory.getCurrentSession().persist(ace);
		assertTrue(ace.getId()>0);
		AccessControlEntryHibernate dbAce = (AccessControlEntryHibernate)sessionFactory.getCurrentSession().get(AccessControlEntryHibernate.class, ace.getId());
		assertEquals(ace.getId(), dbAce.getId());
		assertFalse(dbAce.isGranting());
		assertEquals(ace.getRoleName(), dbAce.getRoleName());
	}
	
	
}
