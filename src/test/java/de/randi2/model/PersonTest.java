package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.exceptions.ValidationException;
import de.randi2.test.utility.AbstractDomainTest;


public class PersonTest extends AbstractDomainTest<Person> {

	private Person validPerson;

	public PersonTest() {
		super(Person.class);
	}

	@Before
	public void setUp() {
		validPerson = super.factory.getPerson();
		validPerson.setLogin(factory.getLogin());
		validPerson.getLogin().setPerson(validPerson);
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
//		TODO
//		assertNull(p.getGender());

		assertEquals("", p.getEmail());
		assertEquals("", p.getPhone());
		assertEquals("", p.getMobile());
		assertEquals("", p.getFax());

		assertNull(p.getAssistant());
		assertNull(p.getTrialSite());

		assertNull(p.getLogin());
	//	assertEquals(0, p.getRoles().size());
	}

	@Test
	public void testSurname() {
		validPerson.setSurname(stringUtil.getWithLength(1));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertValid(validPerson);

		validPerson.setSurname(stringUtil
				.getWithLength(Person.MAX_NAME_LENGTH));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertValid(validPerson);

		validPerson.setSurname(stringUtil
				.getWithLength(Person.MAX_NAME_LENGTH + 1));
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
	public void testFirstname() {
		validPerson.setFirstname(stringUtil.getWithLength(1));
		assertEquals(stringUtil.getLastString(), validPerson.getFirstname());
		assertValid(validPerson);

		validPerson.setFirstname(stringUtil
				.getWithLength(Person.MAX_NAME_LENGTH));
		assertEquals(stringUtil.getLastString(), validPerson.getFirstname());
		assertValid(validPerson);

		validPerson.setFirstname(stringUtil
				.getWithLength(Person.MAX_NAME_LENGTH + 1));
		assertEquals(stringUtil.getLastString(), validPerson.getFirstname());
		assertInvalid(validPerson);

		validPerson.setFirstname("");
		assertEquals("", validPerson.getFirstname());
		assertInvalid(validPerson);

		validPerson.setFirstname(null);
		assertEquals("", validPerson.getFirstname());
		assertInvalid(validPerson);
	}
	
	@Test
	public void testTitle() {
		validPerson.setTitle(stringUtil.getWithLength(1));
		assertEquals(stringUtil.getLastString(), validPerson.getTitle());
		assertValid(validPerson);

		validPerson.setTitle(stringUtil
				.getWithLength(Person.MAX_TITLE_LENGTH));
		assertEquals(stringUtil.getLastString(), validPerson.getTitle());
		assertValid(validPerson);

		validPerson.setTitle(stringUtil
				.getWithLength(Person.MAX_TITLE_LENGTH + 1));
		assertEquals(stringUtil.getLastString(), validPerson.getTitle());
		assertInvalid(validPerson);

		validPerson.setTitle("");
		assertEquals("", validPerson.getTitle());
		assertValid(validPerson);

		validPerson.setTitle(null);
		assertEquals("", validPerson.getTitle());
		assertValid(validPerson);
	}
	
	@Test
	public void testPhone(){
		String phonenumber = "01234/6789";
		validPerson.setPhone(phonenumber);
		assertEquals(phonenumber, validPerson.getPhone());
		assertValid(validPerson);
		
		phonenumber = "123345";
		validPerson.setPhone(phonenumber);
		assertEquals(phonenumber, validPerson.getPhone());
		assertValid(validPerson);
		
		
		validPerson.setPhone("012a/6789");
		assertInvalid(validPerson);
		
		validPerson.setPhone("0123345");
		assertValid(validPerson);
		
		validPerson.setPhone("");
		try {
			hibernateTemplate.saveOrUpdate(validPerson);
			fail("should throw exception");
		} catch (InvalidStateException e) {
			InvalidValue[] invalidValues = e.getInvalidValues();
			assertEquals(1, invalidValues.length);
		}
		
		validPerson.setPhone(null);
		try {
			hibernateTemplate.saveOrUpdate(validPerson);
			fail("should throw exception");
		} catch (InvalidStateException e) {
			InvalidValue[] invalidValues = e.getInvalidValues();
			assertEquals(1, invalidValues.length);
		}
	}

	
	@Test
	public void testMobile(){
		String mobilenumber = "01234/6789";
		validPerson.setMobile(mobilenumber);
		assertEquals(mobilenumber, validPerson.getMobile());
		assertValid(validPerson);
		
		validPerson.setMobile("123345");
		assertValid(validPerson);
		
		
		validPerson.setMobile("012a/6789");
		assertInvalid(validPerson);
		
		validPerson.setMobile("0123345");
		assertValid(validPerson);
		
		validPerson.setMobile("");
		assertValid(validPerson);
		
		validPerson.setMobile(null);
		assertValid(validPerson);
		
	}
	
	@Test
	public void testFax(){
		String faxnumber = "01234/6789";
		validPerson.setFax(faxnumber);
		assertEquals(faxnumber, validPerson.getFax());
		assertValid(validPerson);
		
		validPerson.setFax("123345");
		assertValid(validPerson);
		
		
		validPerson.setFax("012a/6789");
		assertInvalid(validPerson);
		
		validPerson.setFax("0123345");
		assertValid(validPerson);
		
		validPerson.setFax("");
		assertValid(validPerson);
		
		validPerson.setFax(null);
		assertValid(validPerson);
		
	}
	
	@Test
	public void testEMail() {
		String[] validEMails = {"abc@def.de", "h@alo.com", "info@2wikipedia.org", "mue5ller@gmx.net", "max-muster@raf.uk", "xyz@test.info"};
		for (String s: validEMails){
			validPerson.setEmail(s);
			assertValid(validPerson);
			
			try{
			validPerson.checkValue("eMail", s);
			}catch (ValidationException e) {
				fail(e.getMessage());
			}
		}

		validPerson.setEmail(null);
		assertInvalid(validPerson);

		validPerson.setEmail("");
		try {
			hibernateTemplate.saveOrUpdate(validPerson);
			fail("should throw exception");
		} catch (InvalidStateException e) {
			InvalidValue[] invalidValues = e.getInvalidValues();
			assertEquals(2, invalidValues.length);
		}

		String[] invalidEmails = new String[] {  "without at","toomuch@@", "@test.org", "ab..c@de-dg.com",
				"without@domain" , "abc@def.abcde"};
		for (String s : invalidEmails) {
			validPerson.setEmail(s);
			assertInvalid(validPerson);
		}
	}

	@Test
	public void testCheckValueSurname(){
		try{
		validPerson.checkValue("surname", "");
		fail("< >: is not valid");
		}catch (ValidationException e) {
			// TODO: handle exception
		}
		
		try{
			validPerson.checkValue("surname", stringUtil.getWithLength(Person.MAX_NAME_LENGTH + 20));
			fail("< >: is not valid");
			}catch (ValidationException e) {
				// TODO: handle exception
			}
	}
	
	@Test
	public void testCheckValueFirstname(){
		try{
		validPerson.checkValue("firstname", "");
		fail("< >: is not valid");
		}catch (ValidationException e) {
		}
		
		try{
			validPerson.checkValue("firstname", stringUtil.getWithLength(Person.MAX_NAME_LENGTH + 20));
			fail("< >: is not valid");
			}catch (ValidationException e) {
			}
	}
	
	@Test
	public void testCheckValueTitle(){
		
		try{
			validPerson.checkValue("title", stringUtil.getWithLength(Person.MAX_TITLE_LENGTH + 20));
			fail("< >: is not valid");
			}catch (ValidationException e) {
			}
	}
	
	@Test
	public void testCheckValueGender() {
		validPerson.setSex(Gender.MALE);
		assertEquals(Gender.MALE, validPerson.getSex());
		assertValid(validPerson);

		validPerson.setSex(null);
		try {
			validPerson.checkValue("gender", validPerson.getSex());
			fail("should throw exception");
		} catch (ValidationException e) {
		}
	}
	
	@Test
	public void testCheckValueTelephone() {
		String[] validNumbers = { "01234/45678", "+49 123456 789123",
			"(123456)67890", "123456789"};
		
		for(String s: validNumbers){
			validPerson.setPhone(s);
			try{
				validPerson.checkValue("phone", validPerson.getPhone());
			}catch (ValidationException e) {
				fail("Telephonenumber schould be ok");
			}
		}
		
		String[] invalidNumbers ={ "abc1234/09707", "12345d56789", "", null };
		for(String s: invalidNumbers){
			validPerson.setPhone(s);
			try{
				validPerson.checkValue("phone", validPerson.getPhone());
				fail("Telephonenumber " +s+" schould be wrong");
			}catch (ValidationException e) {
				
			}
		}
	}

	// TODO This test ist not running. Has the db-layout changed?
	@Test
	public void testLogin() {
		Login l = factory.getLogin();
		l.setPerson(validPerson);
		hibernateTemplate.save(l);
		validPerson.setLogin(l);
		assertNotNull(validPerson.getLogin());
		hibernateTemplate.saveOrUpdate(validPerson);

		Person p = (Person) hibernateTemplate.get(Person.class, validPerson
				.getId());
		assertNotNull(p);
		assertEquals(validPerson.getSurname(), p.getSurname());
		assertNotNull(p.getLogin());
		assertEquals(validPerson.getLogin().getId(), p.getLogin().getId());

	}

	@Test
	public void testAssistant() {
		Person assistant = factory.getPerson();
		assistant.setSurname(stringUtil.getWithLength(20));
		validPerson.setAssistant(assistant);
		validPerson.setSurname(stringUtil.getWithLength(20));
		assertNotNull(validPerson.getAssistant());
		hibernateTemplate.saveOrUpdate(validPerson.getAssistant());
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
	public void testTrialSite() {
		TrialSite trialSite = factory.getTrialSite();
		hibernateTemplate.save(trialSite.getContactPerson());
		hibernateTemplate.saveOrUpdate(trialSite);
		
		validPerson.setSurname(stringUtil.getWithLength(20));
		validPerson.setTrialSite(trialSite);
		assertNotNull(validPerson.getTrialSite());

		hibernateTemplate.saveOrUpdate(validPerson);

		assertTrue(validPerson.getTrialSite().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) hibernateTemplate.get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getTrialSite());
		assertEquals(trialSite.getId(), p.getTrialSite().getId());
	}
	
	@Test
	public void databaseIntegrationTest() {
		hibernateTemplate.save(validPerson);
		assertTrue(validPerson.getId()>0);
		assertTrue(validPerson.getLogin().getId()>0);
		Person person = (Person)hibernateTemplate.get(Person.class, validPerson.getId());
		assertEquals(validPerson.getId(), person.getId());
		assertEquals(validPerson.getLogin().getId(), person.getLogin().getId());
	}
}
