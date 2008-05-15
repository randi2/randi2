package de.randi2test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractDomainTest<TC extends AbstractDomainObject> {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Autowired
	protected TestStringUtil stringUtil;
	
	protected Class<TC> testClass;

	protected AbstractDomainTest(Class<TC> _testClass) {
		this.testClass = _testClass;
	}

	protected void assertValid(TC validDO) {
		hibernateTemplate.saveOrUpdate(validDO);
		long id = validDO.getId();
		assertTrue(id != Long.MIN_VALUE);
		
	}

	protected void assertInvalid(TC invalidDO, String[] messages) {
		try {
			hibernateTemplate.saveOrUpdate(invalidDO);
			fail("should throw exception");
		} catch (InvalidStateException e) {
			InvalidValue[] invalids = e.getInvalidValues();
			assertEquals(messages.length, invalids.length);
			// TODO Reihenfolgeproblem
			for (int i = 0; i < messages.length; i++) {
				assertEquals(testClass, invalids[i].getBeanClass());
				// TODO Typ und Feld der Fehlermeldung Test 
				//assertEquals(messages[i], invalids[i].getMessage());
			}
		}
	}
	
	protected void assertInvalid(TC invalidDO){
		this.assertInvalid(invalidDO, new String[]{""});
	}
	
}
