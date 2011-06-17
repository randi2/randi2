package de.randi2.core.unit.validations;

import static org.junit.Assert.*;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.randomization.ResponseAdaptiveRConfig;
import de.randi2.testUtility.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class ResponseAdaptiveRandomizationValidatorTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Autowired private DomainObjectFactory factory;

	@Test
	public void testCofigurationValid() {
		ResponseAdaptiveRConfig config = factory.getResponseAdaptiveRConfig(10,
				4, 50, 50, 50);
		assertEquals(0, validator.validate(config).size());
	}

	@Test
	public void testCountSuccessInvalid() {
		ResponseAdaptiveRConfig config = factory.getResponseAdaptiveRConfig(9, 4, 50,50,50);
		assertEquals(1, validator.validate(config).size());
	}
	
	@Test
	public void testCountFailureInvalid() {
		ResponseAdaptiveRConfig config = factory.getResponseAdaptiveRConfig(10, 3, 50,50,50);
		assertEquals(1, validator.validate(config).size());
	}
	
	@Test
	public void testCountFailureGraterThanSuccess() {
		ResponseAdaptiveRConfig config = factory.getResponseAdaptiveRConfig(6, 8, 50,50,50);
		assertEquals(1, validator.validate(config).size());
	}

}
