package de.randi2test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.exceptions.ValidationException;
import de.randi2test.utility.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring.xml",
		"/META-INF/subconfig/test.xml" })
public class PersonTest extends AbstractDomainTest<Person> {

	private Person validPerson;

	public PersonTest() {
		super(Person.class);
	}

	@Before
	public void setUp() {
		validPerson = super.factory.getPerson();
	}

	@Test(expected = ValidationException.class)
	public void testValidation() {
		validPerson.checkValue("surname", stringUtil.getWithLength(400));
		validPerson.checkValue("eMail", stringUtil.getWithLength(100));
	}

	@Test
	public void testConstructor() {
		Person p = new Person();
		assertEquals("", p.getFirstname());
		assertEquals("", p.getSurname());
		assertEquals("", p.getTitle());
		assertNull(p.getGender());

		assertEquals("", p.getEMail());
		assertEquals("", p.getPhone());
		assertEquals("", p.getMobile());
		assertEquals("", p.getFax());

		assertNull(p.getAssistant());
		assertNull(p.getCenter());

		assertNull(p.getLogin());
		assertEquals(0, p.getRoles().size());
	}

	@Test
	public void testSurname() {
		validPerson.setSurname(stringUtil.getWithLength(1));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertValid(validPerson);

		validPerson.setSurname(stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertValid(validPerson);

		validPerson.setSurname(stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertInvalid(validPerson);

		validPerson.setSurname("");
		assertEquals("", validPerson.getSurname());
		assertInvalid(validPerson);

		validPerson.setSurname(null);
		assertEquals("", validPerson.getSurname());
		assertInvalid(validPerson);
	}

	@Test
	public void testLogin() {
		Login l = factory.getLogin();
		validPerson.setSurname(stringUtil.getWithLength(20));
		l.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH));
		validPerson.setLogin(l);
		assertNotNull(validPerson.getLogin());
		hibernateTemplate.saveOrUpdate(validPerson);

		Person p = (Person) hibernateTemplate.get(Person.class, validPerson
				.getId());
		assertNotNull(p);
		assertEquals(validPerson.getSurname(), p.getSurname());
		assertNotNull(p.getLogin());
		assertEquals(validPerson.getLogin().getId(), p.getLogin().getId());
		Person p1 = p.getLogin().getPerson();

	}

	@Test
	public void testAssistant() {
		Person assistant = factory.getPerson();
		assistant.setSurname(stringUtil.getWithLength(20));
		validPerson.setAssistant(assistant);
		validPerson.setSurname(stringUtil.getWithLength(20));
		assertNotNull(validPerson.getAssistant());

		hibernateTemplate.saveOrUpdate(validPerson);

		assertTrue(validPerson.getAssistant().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) hibernateTemplate.get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getAssistant());
		assertEquals(assistant.getId(), p.getAssistant().getId());
	}

	@Test
	public void testCenter() {
		Center center = factory.getCenter();
		center.setName(stringUtil.getWithLength(20));
		validPerson.setSurname(stringUtil.getWithLength(20));
		validPerson.setCenter(center);
		assertNotNull(validPerson.getCenter());

		hibernateTemplate.saveOrUpdate(validPerson);

		assertTrue(validPerson.getCenter().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) hibernateTemplate.get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getCenter());
		assertEquals(center.getId(), p.getCenter().getId());
	}

	@Test
	public void testEMail() {
		validPerson.setSurname(stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH));
		validPerson.setEMail("abc@def.xy");
		assertValid(validPerson);
		
		validPerson.setEMail(null);
		try {
			hibernateTemplate.saveOrUpdate(validPerson);
		} catch (InvalidStateException e) {
			InvalidValue[] invalidValues = e.getInvalidValues();
			assertEquals(2, invalidValues.length);
		}
		
		validPerson.setEMail("");
		try {
			hibernateTemplate.saveOrUpdate(validPerson);
		} catch (InvalidStateException e) {
			InvalidValue[] invalidValues = e.getInvalidValues();
			assertEquals(1, invalidValues.length);
		}

		String[] invalidEmails = new String[] { "without at", "toomuch@@",
				"without@domain" };
		for (String s : invalidEmails) {
			validPerson.setEMail(s);
			try {
				hibernateTemplate.saveOrUpdate(validPerson);
			} catch (InvalidStateException e) {
				InvalidValue[] invalidValues = e.getInvalidValues();
				assertEquals(1, invalidValues.length);
				assertEquals("not a well-formed email address",
						invalidValues[0].getMessage());
			}
		}
	}

	@Test
	public void testPersonRolle() {
		// TODO
	}

}
