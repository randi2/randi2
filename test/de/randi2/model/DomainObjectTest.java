package de.randi2.model;

import static org.junit.Assert.assertEquals;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/de/randi2/applicationContext.xml" })
public class DomainObjectTest<TC extends AbstractDomainObject> {

	protected ClassValidator<TC> validator;
	protected Class<TC> testClass;

	protected DomainObjectTest(Class<TC> _testClass) {
		this.testClass = _testClass;
		this.validator = new ClassValidator<TC>(testClass);
	}

	protected void assertValid(TC validDO) {
		InvalidValue[] invalids = this.validator
				.getInvalidValues(validDO);
		assertEquals(0, invalids.length);
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
		InvalidValue[] invalids = this.validator
				.getInvalidValues(invalidDO);
		assertEquals(messages.length, invalids.length);
		for(int i = 0; i < messages.length; i++){
			assertEquals(testClass, invalids[i].getBeanClass());
			assertEquals(messages[i], invalids[i].getMessage());
		}
	}
}
