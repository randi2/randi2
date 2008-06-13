package de.randi2test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2test.utility.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring.xml", "/META-INF/subconfig/test.xml"})
public class AbstractDomainObjectTest extends AbstractDomainTest<AbstractDomainObject> {

	
	private Trial validObject;
	
	public AbstractDomainObjectTest() {
		super(AbstractDomainObject.class);
	}
	
	@Before
	public void setUp(){
		validObject = factory.getTrial();
		validObject.setName("Aspirin vs. Placebo");
	}
	
	@Test
	public void testVersion(){
		hibernateTemplate.save(validObject);
		int version = validObject.getVersion();
		Trial v1 = (Trial) hibernateTemplate.get(Trial.class, validObject.getId());
		Trial v2 = (Trial) hibernateTemplate.get(Trial.class, validObject.getId());
		
		v1.setName("Aenderung 1");
		hibernateTemplate.saveOrUpdate(v1);
		assertTrue(version < v1.getId());	
		
		v2.setName("Aenderung 2");
		
		try{
			hibernateTemplate.saveOrUpdate(v2);
			fail("Should fail because of Version Conflicts");
		}
		catch(HibernateOptimisticLockingFailureException e){
			
		}
		
		Trial v3 = (Trial) hibernateTemplate.get(Trial.class, validObject.getId());
		assertEquals(v1.getName(), v3.getName());
		
		hibernateTemplate.refresh(v2);
		v2.setName("Aenderung 2");
		hibernateTemplate.saveOrUpdate(v2);
		Trial v4 = (Trial) hibernateTemplate.get(Trial.class, validObject.getId());
		assertEquals(v2.getName(), v4.getName());
	}
	
	

}
