package de.randi2.model;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.test.utility.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml"})
public class AbstractDomainObjectTest extends AbstractDomainTest<AbstractDomainObject> {

	private Login domainObject;

	public AbstractDomainObjectTest() {
		super(AbstractDomainObject.class);
	}

	@Before
	public void setUp() {
		domainObject = factory.getLogin();
	}

	@Test
	public void testCreate() {
		domainObject = new Login();

		assertEquals(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getVersion() < 0);
		assertNull(domainObject.getUpdatedAt());
		assertNull(domainObject.getCreatedAt());
	}



	@Test
	public void testTimestamps() {
		domainObject = factory.getLogin();
		hibernateTemplate.persist(domainObject);

		assertNotNull(domainObject.getCreatedAt());
		assertNotNull(domainObject.getUpdatedAt());

		domainObject.setUsername("hello@world.com");
		hibernateTemplate.update(domainObject);

		assertTrue(domainObject.getCreatedAt().before(domainObject.getUpdatedAt()) || domainObject.getCreatedAt().equals(domainObject.getUpdatedAt()));
	}

	@Test
	public void testSave(){
		domainObject = factory.getLogin();
		hibernateTemplate.persist(domainObject);
		hibernateTemplate.flush();

		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getId() > 0);

	//	assertEquals(0, domainObject.getVersion());
	}

	// TODO Some hibernate problem, should be fixed
	@Test
	public void testOptimisticLocking() {
		hibernateTemplate.save(domainObject);
		int version = domainObject.getVersion();
		Login v2 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		Login v1 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		
		v1.setPassword("Aenderung$1");
		hibernateTemplate.update(v1);
		assertTrue(version < v1.getVersion());
		hibernateTemplate.flush();
		//v2.setPassword("Aenderung$2");

		try {
			hibernateTemplate.update(v2);
			fail("Should fail because of Version Conflicts");
		} catch (HibernateOptimisticLockingFailureException e) {
			hibernateTemplate.evict(v2);
		}

		Login v3 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		assertEquals(v1.getPassword(), v3.getPassword());
		v2 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
//		hibernateTemplate.refresh(v2);
		v2.setPassword("Aenderung$2");
		hibernateTemplate.saveOrUpdate(v2);
		Login v4 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		assertEquals(v2.getPassword(), v4.getPassword());
	}
	
	
	@Test
	public void testGetRequieredFields(){
		Map<String, Boolean> map = domainObject.getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("active")) {assertFalse(map.get(key));} 
			else if(key.equals("numberWrongLogins")) {assertFalse(map.get(key));} 
			else if(key.equals("prefLocale")) {assertFalse(map.get(key));}  
			else if(key.equals("person")) {assertTrue(map.get(key));} 
			else if(key.equals("username")) {assertTrue(map.get(key));} 
			else if(key.equals("password")) {assertTrue(map.get(key)); }
			else if(key.equals("lastLoggedIn")) {assertFalse(map.get(key));} 
			else if(key.equals("lockTime")) {assertFalse(map.get(key));} 
			else if(key.equals("roles")) {assertFalse(map.get(key)); }
			else if(key.equals("MAX_WRONG_LOGINS")) {assertFalse(map.get(key));}  
			else if(key.equals("MILIS_TO_LOCK_USER")) {assertFalse(map.get(key));} 
			else if(key.equals("MAX_USERNAME_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("MIN_USERNAME_LENGTH")) {assertFalse(map.get(key));}
			else if(key.equals("MAX_PASSWORD_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("MIN_PASSWORD_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("HASH_PASSWORD_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("serialVersionUID")) {assertFalse(map.get(key));}
			else if(key.equals("$VRc")) {assertFalse(map.get(key));}
			else fail(key + " not checked");
		}
	}
}
