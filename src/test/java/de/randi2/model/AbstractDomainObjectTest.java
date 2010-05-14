package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.HashMap;
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
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertNotNull(object.getRequiredFields());
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		object.setRequiredFields(map);
		assertEquals(map, object.getRequiredFields());
	}
	
	@Test
	public void testGetUiName(){
		AbstractDomainObject object = new AbstractDomainObject(){
			@Override
			public String getUIName() {
				return super.getUIName() +"";
			}
		};
		assertNotNull(object.getUIName());
	}
	
	@Test
	public void testUpdatedAt(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertNull(object.getUpdatedAt());
		GregorianCalendar cal = new GregorianCalendar();
		object.setUpdatedAt(cal);
		assertEquals(cal, object.getUpdatedAt());
		object.setUpdatedAt(null);
		assertNull(object.getUpdatedAt());
	}
	
	@Test
	public void testCreatedAt(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertNull(object.getCreatedAt());
		GregorianCalendar cal = new GregorianCalendar();
		object.setCreatedAt(cal);
		assertEquals(cal, object.getCreatedAt());
		object.setCreatedAt(null);
		assertNull(object.getUpdatedAt());
	}
	
	
	@Test
	public void testVersion(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertTrue(object.getVersion()<0);
		object.setVersion(123);
		assertEquals(123, object.getVersion());
	}
	
	@Test
	public void testId(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertEquals(Integer.MIN_VALUE, object.getId());
		object.setId(123);
		assertEquals(123, object.getId());
	}
	
	
	@Test
	public void testHashCodeEquals(){
		AbstractDomainObject object1 = new Trial();
		AbstractDomainObject object2 = new Trial();
		object1.setVersion(0);
		object2.setVersion(0);
		assertEquals(object1, object2);
		assertEquals(object1.hashCode(), object2.hashCode());
		object2.setVersion(1);
		assertFalse(object1.equals(object2));
		assertFalse(object1.equals(null));
		assertFalse(object1.equals(new TreatmentArm()));
		object1.setVersion(1);
		assertEquals(object1, object2);
		assertEquals(object1.hashCode(), object2.hashCode());
		object1.setId(1);
		assertFalse(object1.equals(object2));
		object2.setId(1);
		assertEquals(object1, object2);
		assertEquals(object1.hashCode(), object2.hashCode());
		assertEquals(object1, object1);
		assertEquals(object1.hashCode(), object1.hashCode());
	}
	
	
	@Test
	public void testToString(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertNotNull(object.toString());
	}
	
	@Test
	public void testCheckValue(){
		AbstractDomainObject object = new Trial();
		//AbstractDomainObject has no constraints
		object.checkValue("createdAt", null);
	}
}
