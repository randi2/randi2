package de.randi2.core.unit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.exceptions.ValidationException;
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
	public void testInitizialization() {
		domainObject = new Login();

		assertEquals(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getVersion() < 0);
		assertNull(domainObject.getUpdatedAt());
		assertNull(domainObject.getCreatedAt());
	}

	
	@Test
	public void testId(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertEquals(Integer.MIN_VALUE, object.getId());
		object.setId(123);
		assertEquals(123, object.getId());
	}


	@Test
	public void testGetRequieredFields(){
		AbstractDomainObject object = new AbstractDomainObject(){
			@NotNull private String newField;
		};
		assertNotNull(object.getRequiredFields());
		Map<String, Boolean> requiredFields = object.getRequiredFields();
		for(String key : requiredFields.keySet()){
			if(key.equals("newField")) {assertTrue(requiredFields.get(key));} 
			else if(key.equals("this$0")) {assertFalse(requiredFields.get(key));}
			else fail(key + " not checked");
		}
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
	public void testCreatedAtNull(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		object.setCreatedAt(null);
		assertNull(object.getCreatedAt());
		
	}
	
	@Test
	public void testSetCreatedAt(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		GregorianCalendar cal = new GregorianCalendar();
		object.setCreatedAt(cal);
		assertEquals(cal, object.getCreatedAt());
	}
	
	@Test
	public void testUpdatedAtNull(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		object.setUpdatedAt(null);
		assertNull(object.getUpdatedAt());
	}
		
	@Test
	public void testSetUpdatedAt(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		GregorianCalendar cal = new GregorianCalendar();
		object.setUpdatedAt(cal);
		assertEquals(cal, object.getUpdatedAt());
	}
	
	@Test
	public void testVersion(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertTrue(object.getVersion()<0);
		object.setVersion(123);
		assertEquals(123, object.getVersion());
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
	public void testBeforeCreate(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		GregorianCalendar dateInThePast = new GregorianCalendar(2000,1,1);
		object.setCreatedAt(dateInThePast);
		object.setUpdatedAt(dateInThePast);
		object.beforeCreate();
		assertTrue(dateInThePast.before(object.getCreatedAt()));
		assertTrue(dateInThePast.before(object.getUpdatedAt()));
		
	}
	
	@Test
	public void testBeforeUpdate(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		GregorianCalendar dateInThePast = new GregorianCalendar(2000,1,1);
		object.setCreatedAt(dateInThePast);
		object.setUpdatedAt(dateInThePast);
		object.beforeUpdate();
		assertEquals(dateInThePast, object.getCreatedAt());
		assertTrue(dateInThePast.before(object.getUpdatedAt()));
		
	}
	
	@Test
	public void testToString(){
		AbstractDomainObject object = new AbstractDomainObject(){};
		assertNotNull(object.toString());
	}
	
	@Test
	public void testCheckValueWithIncorrectValue(){
		AbstractDomainObject object = new AbstractDomainObject(){
			@NotNull private String newField;
		};
		try{
			object.checkValue("newField", null);
			fail("should throw an ValidationException");
		}catch (ValidationException e) {}
	}
	
	@Test
	public void testCheckValueWithCorrectValue(){
		AbstractDomainObject object = new AbstractDomainObject(){
			@NotNull private String newField;
		};
		try{
			object.checkValue("newField", "string");
		}catch (ValidationException e) {
			fail("should not throw an ValidationException");
		}
	}
}
