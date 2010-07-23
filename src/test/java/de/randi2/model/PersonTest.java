package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.exceptions.ValidationException;
import de.randi2.test.utility.AbstractDomainTest;

@Transactional
public class PersonTest extends AbstractDomainTest<Person> {

	private Person validPerson;

	public PersonTest() {
		super(Person.class);
	}

	@Before
	public void setUp() {
		super.setUp();
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
		
		assertInvalid(validPerson);
		
		
		validPerson.setPhone(null);
		assertInvalid(validPerson);
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
		assertInvalid(validPerson);

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
			validPerson.checkValue("sex", validPerson.getSex());
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

	@Test
	public void testLogin() {
		Login l = factory.getLogin();
		l.setPerson(validPerson);
		validPerson.setLogin(l);
		assertNotNull(validPerson.getLogin());
		sessionFactory.getCurrentSession().saveOrUpdate(validPerson);
		assertEquals(validPerson.getLogin().getId(),l.getId());
		Person p = (Person) sessionFactory.getCurrentSession().get(Person.class, validPerson
				.getId());
		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertEquals(validPerson.getFirstname(), p.getFirstname());
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
		sessionFactory.getCurrentSession().saveOrUpdate(validPerson.getAssistant());
		sessionFactory.getCurrentSession().saveOrUpdate(validPerson);

		assertTrue(validPerson.getAssistant().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) sessionFactory.getCurrentSession().get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getAssistant());
		assertEquals(assistant.getId(), p.getAssistant().getId());
	}

	@Test
	public void testTrialSite() {
		TrialSite trialSite = factory.getTrialSite();
		sessionFactory.getCurrentSession().save(trialSite.getContactPerson());
		sessionFactory.getCurrentSession().saveOrUpdate(trialSite);
		
		validPerson.setSurname(stringUtil.getWithLength(20));
		validPerson.setTrialSite(trialSite);
		assertNotNull(validPerson.getTrialSite());

		sessionFactory.getCurrentSession().saveOrUpdate(validPerson);

		assertTrue(validPerson.getTrialSite().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) sessionFactory.getCurrentSession().get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getTrialSite());
		assertEquals(trialSite.getId(), p.getTrialSite().getId());
	}
	
	@Test
	public void databaseIntegrationTest() {
		sessionFactory.getCurrentSession().save(validPerson);
		assertTrue(validPerson.getId()>0);
		assertTrue(validPerson.getLogin().getId()>0);
		Person person = (Person)sessionFactory.getCurrentSession().get(Person.class, validPerson.getId());
		assertEquals(validPerson.getId(), person.getId());
		assertEquals(validPerson.getLogin().getId(), person.getLogin().getId());
	}
	
	@Test
	public void testGetRequieredFields(){
		Map<String, Boolean> map = validPerson.getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("surname")) {assertTrue(map.get(key));} 
			else if(key.equals("firstname")) {assertTrue(map.get(key));} 
			else if(key.equals("title")) {assertFalse(map.get(key));}  
			else if(key.equals("sex")) {assertTrue(map.get(key));} 
			else if(key.equals("email")) {assertTrue(map.get(key));} 
			else if(key.equals("phone")) {assertTrue(map.get(key)); }
			else if(key.equals("mobile")) {assertFalse(map.get(key));} 
			else if(key.equals("fax")) {assertFalse(map.get(key));} 
			else if(key.equals("assistant")) {assertFalse(map.get(key)); }
			else if(key.equals("trialSite")) {assertFalse(map.get(key)); }
			else if(key.equals("login")) {assertFalse(map.get(key)); }
			else if(key.equals("MAX_TITLE_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("MAX_NAME_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("serialVersionUID")) {assertFalse(map.get(key));}
			else if(key.equals("$VRc")) {assertFalse(map.get(key));}
			else fail(key + " not checked");
		}
	}
	
	@Test
	public void testUiName(){
		validPerson.setSurname("surname");
		validPerson.setFirstname("firstname");
		assertEquals("surname, firstname", validPerson.getUIName());
	}
	
	@Test
	public void testToString(){
		assertNotNull(validPerson.toString());
	}
	
	@Test
	public void testEqualsHashCode(){
		Person person1 = new Person();
		Person person2 = new Person();
		person1.setId(0);
		person2.setId(0);
		person1.setVersion(0);
		person2.setVersion(0);
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
		person1.setId(1);
		
		assertFalse(person1.equals(person2));
		person1.setId(0);
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
		
		person1.setVersion(1);
		assertFalse(person1.equals(person2));
		person1.setVersion(0);
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
		
		person1.setSurname("test");
		assertFalse(person1.equals(person2));
		person2.setSurname("test");
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
		
		person1.setFirstname("test");
		assertFalse(person1.equals(person2));
		person2.setFirstname("test");
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
		
		person1.setSex(Gender.FEMALE);
		person2.setSex(Gender.MALE);
		assertFalse(person1.equals(person2));
		person2.setSex(Gender.FEMALE);
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
		
		assertFalse(person1.equals(null));
		assertFalse(person1.equals(new TreatmentArm()));
	}
}
