package de.randi2.testUtility.utility;

import static org.junit.Assert.assertTrue;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
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
		ClassValidator<TC> classValidator = new ClassValidator<TC>((Class<TC>) validDO.getClass());
		InvalidValue[] invalids = classValidator.getInvalidValues(validDO);
		StringBuilder message = new StringBuilder();
		for(InvalidValue v : invalids){
			message.append(v.getPropertyName()).append(" (").append(v.getValue()).append(")").append(": ").append(v.getMessage()).append("\n");
		}
		assertTrue(message.toString(), invalids.length==0); 
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
			ClassValidator<TC> classValidator = new ClassValidator<TC>((Class<TC>) invalidDO.getClass());
			InvalidValue[] invalids = classValidator.getInvalidValues(invalidDO);
			StringBuilder message = new StringBuilder();
			for(String s : messages){
				message.append(s + "  |  ");
			}
			assertTrue(message.toString(),invalids.length>0); 
	}
	
	protected void assertInvalid(TC invalidDO, String message) {
		this.assertInvalid(invalidDO, new String[]{message});
	}
	
	protected void assertInvalid(TC invalidDO){
		this.assertInvalid(invalidDO, new String[]{""});
	}
	
}
