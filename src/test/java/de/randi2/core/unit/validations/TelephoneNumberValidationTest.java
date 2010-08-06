package de.randi2.core.unit.validations;

import static org.junit.Assert.*;

import org.hibernate.validator.ClassValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class TelephoneNumberValidationTest {

	private ClassValidator<Person> personValidator;
	

	@Test
	public void testTelephoneNumberValidation() {
		personValidator = new ClassValidator<Person>(Person.class);

		String[] validPhoneNumber = { "01234/45678", "+49 123456 789123",
				"(123456)67890", "123456789", "", null };

		for (String s : validPhoneNumber) {

			assertEquals("Right number [" + s + "]", 0, personValidator
					.getPotentialInvalidValues("mobile", s).length);
		}

		String[] invalidPhoneNumber = { "abc1234/09707", "12345d56789" };
		for (String s : invalidPhoneNumber) {
			assertEquals("Wrong number [" + s + "]", 1, personValidator
					.getPotentialInvalidValues("mobile", s).length);

		}
	}

}
