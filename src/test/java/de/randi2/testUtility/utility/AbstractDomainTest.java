package de.randi2.testUtility.utility;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public abstract class AbstractDomainTest<TC extends AbstractDomainObject> {

	@Autowired protected TestStringUtil stringUtil;
	@Autowired protected DomainObjectFactory factory;
	
	protected Class<TC> testClass;

	protected AbstractDomainTest(Class<TC> _testClass) {
		this.testClass = _testClass;
	}

	protected void assertValid(TC validDO) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<TC>> invalids = validator.validate(validDO);
		StringBuilder message = new StringBuilder();
		for(ConstraintViolation<TC> v : invalids){
			message.append(v.getPropertyPath()).append(" (").append(v.getInvalidValue()).append(")").append(": ").append(v.getMessage()).append("\n");
		}
		assertTrue(message.toString(), invalids.size()==0); 
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<TC>> invalids = validator.validate(invalidDO);
			StringBuilder message = new StringBuilder();
			for(String s : messages){
				message.append(s + "  |  ");
			}
			assertTrue(message.toString(),invalids.size()>0); 
	}
	
	protected void assertInvalid(TC invalidDO, String message) {
		this.assertInvalid(invalidDO, new String[]{message});
	}
	
	protected void assertInvalid(TC invalidDO){
		this.assertInvalid(invalidDO, new String[]{""});
	}
	
}
