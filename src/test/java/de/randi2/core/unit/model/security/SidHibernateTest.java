package de.randi2.core.unit.model.security;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.security.SidHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class SidHibernateTest {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void testConstructor(){
		SidHibernate sid = new SidHibernate();
		assertNull(sid.getSidname());
		sid = new SidHibernate("sidname");
		assertNotNull(sid.getSidname());
		assertEquals("sidname", sid.getSidname());
	}
	
	@Test
	public void testEquals(){
		SidHibernate sidH1 = new SidHibernate("sidname");
		SidHibernate sidH2 = new SidHibernate("sidname");
		assertEquals(sidH1, sidH1);
		assertEquals(sidH1, sidH2);
		
		sidH2.setSidname("sidname2");
		assertFalse(sidH1.equals(sidH2));
		assertFalse(sidH2.equals(sidH1));
		
		PrincipalSid sidP = new PrincipalSid("sidname");
		assertEquals(sidH1, sidP);
		sidP = new PrincipalSid("sidname2");
		assertFalse(sidH1.equals(sidP));
		
		GrantedAuthoritySid sidG = new GrantedAuthoritySid("sidname");
		assertEquals(sidH1, sidG);
		sidG = new GrantedAuthoritySid("sidname2");
		assertFalse(sidH1.equals(sidG));
		
		assertFalse(sidH1.equals("TestString"));
	}
	
	@Test
	public void testHashCode(){
		SidHibernate sidH1 = new SidHibernate("sidname");
		SidHibernate sidH2 = new SidHibernate("sidname");
		assertTrue(sidH1.hashCode() == sidH2.hashCode());
		
		
		PrincipalSid sidP = new PrincipalSid("sidname");
		assertTrue(sidH1.hashCode() == sidP.hashCode());
		
		GrantedAuthoritySid sidG = new GrantedAuthoritySid("sidname");
		assertTrue(sidH1.hashCode() == sidG.hashCode());
	}
	
	@Test
	public void testSidName(){
		SidHibernate sidH1 = new SidHibernate("sidname");
		assertEquals("sidname", sidH1.getSidname());
		sidH1.setSidname("sidname1");
		assertEquals("sidname1", sidH1.getSidname());
	}
	
	@Test
	public void testToString(){
		SidHibernate sidH1 = new SidHibernate("sidname");
		assertEquals("sidname", sidH1.toString());
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		SidHibernate sidH1 = new SidHibernate("sidname");
		sessionFactory.getCurrentSession().persist(sidH1);
		assertTrue(sidH1.getId()>0);
		SidHibernate sidH2 = (SidHibernate) sessionFactory.getCurrentSession().get(SidHibernate.class, sidH1.getId());
		assertEquals(sidH1.getId(), sidH2.getId());
		assertEquals(sidH1, sidH2);
	}

}
