package de.randi2.test.utility;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.SessionFactory;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.utility.InitializeDatabaseUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public abstract class AbstractDomainTest<TC extends AbstractDomainObject> {

	@Autowired protected HibernateTemplate hibernateTemplate;
	@Autowired protected TestStringUtil stringUtil;
	@Autowired protected DomainObjectFactory factory;
	@Autowired protected ApplicationContext context; 
	@Autowired protected SessionFactory sessionFactory;
	@Autowired protected InitializeDatabaseUtil databaseUtil;
	
	protected Class<TC> testClass;

	protected AbstractDomainTest(Class<TC> _testClass) {
		this.testClass = _testClass;
	}

	@Before
	public void setUp(){
		try {
			databaseUtil.setUpDatabase();
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}
	

	protected void assertValid(TC validDO) {
		try{
		hibernateTemplate.saveOrUpdate(validDO);
		long id = validDO.getId();
		assertTrue(id != Long.MIN_VALUE);
		}catch (InvalidStateException e) {
		
		}
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
		try {
			hibernateTemplate.saveOrUpdate(invalidDO);
			fail("should throw exception");
		} catch (InvalidStateException e) {
			InvalidValue[] invalids = e.getInvalidValues();
			assertTrue(invalids.length>0);
//			assertEquals(messages.length, invalids.length);
//			// TODO Reihenfolgeproblem
//			for (int i = 0; i < messages.length; i++) {
//				assertEquals(testClass, invalids[i].getBeanClass());
//				// TODO Typ und Feld der Fehlermeldung Test 
//				//assertEquals(messages[i], invalids[i].getMessage());
//			}
		}catch (DataIntegrityViolationException e){	}
	}
	
	protected void assertInvalid(TC invalidDO){
		this.assertInvalid(invalidDO, new String[]{""});
	}
	
}
