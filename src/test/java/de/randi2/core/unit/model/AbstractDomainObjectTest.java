package de.randi2.core.unit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.testUtility.utility.AbstractDomainTest;

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
		
		GregorianCalendar cal = new GregorianCalendar(2000,2,13);
		domainObject.setCreatedAt(cal);
		assertEquals(cal, domainObject.getCreatedAt());

		domainObject.setUpdatedAt(cal);
		assertEquals(cal, domainObject.getUpdatedAt());

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
