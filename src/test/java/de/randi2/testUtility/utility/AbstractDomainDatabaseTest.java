package de.randi2.testUtility.utility;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public abstract class AbstractDomainDatabaseTest<TC extends AbstractDomainObject> {

	
	@Autowired protected TestStringUtil stringUtil;
	@Autowired protected DomainObjectFactory factory;
	@Autowired protected ApplicationContext context; 
	@Autowired protected SessionFactory sessionFactory;
	@Autowired protected InitializeDatabaseUtil databaseUtil;
	
	protected Class<TC> testClass;

	protected AbstractDomainDatabaseTest(Class<TC> _testClass) {
		this.testClass = _testClass;
	}

	@Before
	public void setUp(){
		ManagedSessionContext.bind(sessionFactory.openSession());
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}
	

	protected void assertValid(TC validDO) {
		ClassValidator<TC> classValidator = new ClassValidator<TC>((Class<TC>) validDO.getClass());
		InvalidValue[] invalids = classValidator.getInvalidValues(validDO);
		StringBuilder message = new StringBuilder();
		for(InvalidValue v : invalids){
			message.append(v.getPropertyName()).append(" : ").append(v.getMessage()).append("\n");
		}
		assertTrue(message.toString(), invalids.length==0); 
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
			ClassValidator<TC> classValidator = new ClassValidator<TC>((Class<TC>) invalidDO.getClass());
			InvalidValue[] invalids = classValidator.getInvalidValues(invalidDO);
			assertTrue(invalids.length>0); 
	}
	
	protected void assertInvalid(TC invalidDO){
		this.assertInvalid(invalidDO, new String[]{""});
	}
	
}
