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
import de.randi2.model.Login;
import de.randi2.model.Trial;
import de.randi2test.utility.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml"})
public class AbstractDomainObjectTest extends AbstractDomainTest<AbstractDomainObject> {

	
	private Login validObject;
	
	public AbstractDomainObjectTest() {
		super(AbstractDomainObject.class);
	}
	
	@Before
	public void setUp(){
		validObject = factory.getLogin();
	}
	
	@Test
	public void testVersion(){
		hibernateTemplate.save(validObject);
		int version = validObject.getVersion();
		Login v1 = (Login) hibernateTemplate.get(Login.class, validObject.getId());
		Login v2 = (Login) hibernateTemplate.get(Login.class, validObject.getId());
		
		v1.setPassword("Aenderung$1");
		hibernateTemplate.saveOrUpdate(v1);
		assertTrue(version < v1.getId());	
		
		v2.setPassword("Aenderung$2");
		
		try{
			hibernateTemplate.saveOrUpdate(v2);
			fail("Should fail because of Version Conflicts");
		}
		catch(HibernateOptimisticLockingFailureException e){
			
		}
		
		Login v3 = (Login) hibernateTemplate.get(Login.class, validObject.getId());
		assertEquals(v1.getPassword(), v3.getPassword());
		
		hibernateTemplate.refresh(v2);
		v2.setPassword("Aenderung$2");
		hibernateTemplate.saveOrUpdate(v2);
		Login v4 = (Login) hibernateTemplate.get(Login.class, validObject.getId());
		assertEquals(v2.getPassword(), v4.getPassword());
	}
	
	

}
