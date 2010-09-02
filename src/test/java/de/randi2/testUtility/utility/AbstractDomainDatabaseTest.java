package de.randi2.testUtility.utility;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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
	@Autowired protected InitializeDatabaseUtil databaseUtil;
	
	protected EntityManager entityManager;
	
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	protected Class<TC> testClass;

	protected AbstractDomainDatabaseTest(Class<TC> _testClass) {
		this.testClass = _testClass;
	}

	@Before
	public void setUp(){
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}
	

	protected void assertValid(TC validDO) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<TC>> invalids = validator.validate(validDO);
		StringBuilder message = new StringBuilder();
		for(ConstraintViolation<TC> v : invalids){
			message.append(v.getPropertyPath()).append(" : ").append(v.getMessage()).append("\n");
		}
		assertTrue(message.toString(), invalids.size()==0); 
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<TC>> invalids = validator.validate(invalidDO);
			assertTrue(invalids.size()>0); 
	}
	
	protected void assertInvalid(TC invalidDO){
		this.assertInvalid(invalidDO, new String[]{""});
	}
	
}
