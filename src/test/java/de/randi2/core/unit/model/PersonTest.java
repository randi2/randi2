package de.randi2.core.unit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TreatmentArm;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.exceptions.ValidationException;
import de.randi2.testUtility.utility.AbstractDomainTest;

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
	public void testTitleNull(){
		validPerson.setTitle(null);
		assertEquals(null, validPerson.getTitle());
		assertValid(validPerson);
	}
	
	@Test
	public void testTitleEmpty(){
		validPerson.setTitle("");
		assertEquals("", validPerson.getTitle());
		assertValid(validPerson);
	}
	
	
	@Test
	public void testTitleNotLongerThan20(){
		assertEquals(20, Person.MAX_TITLE_LENGTH);
		validPerson.setTitle(stringUtil.getWithLength(Person.MAX_TITLE_LENGTH+1) );
		assertInvalid(validPerson);
	}
	
	@Test
	public void testTitleCorrectValue(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(Person.MAX_TITLE_LENGTH) };
		for(String s :validValues){
			validPerson.setTitle(s);
			assertEquals(s, validPerson.getTitle());
			assertValid(validPerson);
		}
	}
	
	@Test
	public void testPhoneNotNull(){
		validPerson.setPhone(null);
		assertInvalid(validPerson);
	}
	
	@Test
	public void testPhoneNotEmpty(){
		validPerson.setPhone("");
		assertInvalid(validPerson);
	}
	
	@Test
	public void testPhoneCorrect(){
		String[] validPhoneNumber = { "01234/45678", "+49 123456 789123",
				"(123456)67890", "123456789"};
		for(String s : validPhoneNumber){
			validPerson.setPhone(s);
			assertValid(validPerson);
		}
	}
	
	@Test
	public void testPhoneIncorrect(){
		String[] invalidPhoneNumber = { "abc1234/09707", "12345d56789", "abasd" };
		for(String s : invalidPhoneNumber){
			validPerson.setPhone(s);
			assertInvalid(validPerson);
		}
	}

	
	@Test
	public void testMobileNull(){
		validPerson.setMobile(null);
		assertValid(validPerson);
	}
	
	@Test
	public void testMoblieEmpty(){
		validPerson.setMobile("");
		assertValid(validPerson);
	}
	
	@Test
	public void testMobileCorrect(){
		String[] validPhoneNumber = { "01234/45678", "+49 123456 789123",
				"(123456)67890", "123456789", "", null };
		for(String s : validPhoneNumber){
			validPerson.setMobile(s);
			assertValid(validPerson);
		}
	}
	
	@Test
	public void testMobileIncorrect(){
		String[] invalidPhoneNumber = { "abc1234/09707", "12345d56789", "abasd" };
		for(String s : invalidPhoneNumber){
			validPerson.setMobile(s);
			assertInvalid(validPerson);
		}
	}
	
	@Test
	public void testFaxNull(){
		validPerson.setFax(null);
		assertValid(validPerson);
	}
	
	@Test
	public void testFaxEmpty(){
		validPerson.setFax("");
		assertValid(validPerson);
	}
	
	@Test
	public void testFaxCorrect(){
		String[] validPhoneNumber = { "01234/45678", "+49 123456 789123",
				"(123456)67890", "123456789", "", null };
		for(String s : validPhoneNumber){
			validPerson.setFax(s);
			assertValid(validPerson);
		}
	}
	
	@Test
	public void testFaxIncorrect(){
		String[] invalidPhoneNumber = { "abc1234/09707", "12345d56789", "abasd" };
		for(String s : invalidPhoneNumber){
			validPerson.setFax(s);
			assertInvalid(validPerson);
		}
	}
	
	
	@Test
	public void testEMailNotNull() {
		validPerson.setEmail(null);
		assertInvalid(validPerson);
	}
	
	@Test
	public void testEMailNotEmpty() {
		validPerson.setEmail("");
		assertInvalid(validPerson);
	}
	
	
	@Test
	public void testEMailCorrectValues() {
		String[] validEMails = {"abc@def.de", "h@alo.com", "info@2wikipedia.org", "mue5ller@gmx.net", "max-muster@raf.uk", "xyz@test.info"};
		for (String s: validEMails){
			validPerson.setEmail(s);
			assertValid(validPerson);
		}
	}
	
	@Test
	public void testEMailIncorrectValues(){
		String[] invalidEmails = new String[] {  "without at","toomuch@@", "@test.org", "ab..c@de-dg.com",
				"without@domain" , "abc@def.abcde"};
		for (String s : invalidEmails) {
			validPerson.setEmail(s);
			assertInvalid(validPerson);
		}
	}

	@Test
	public void testSurnameNotNull(){
		validPerson.setSurname(null);
		assertEquals(null, validPerson.getSurname());
		assertInvalid(validPerson);
	}
	
	@Test
	public void testSurnameNotEmpty(){
		validPerson.setSurname("");
		assertEquals("", validPerson.getSurname());
		assertInvalid(validPerson);
	}
	
	
	@Test
	public void testSurnameNotLongerThan50(){
		assertEquals(50, Person.MAX_NAME_LENGTH);
		validPerson.setSurname(stringUtil.getWithLength(Person.MAX_NAME_LENGTH+1) );
		assertInvalid(validPerson);
	}
	
	@Test
	public void testSurnameCorrectValue(){
		String[] validValues= {"A","Abcsdafasd", "Name" , stringUtil.getWithLength(Person.MAX_NAME_LENGTH) };
		for(String s :validValues){
			validPerson.setSurname(s);
			assertEquals(s, validPerson.getSurname());
			assertValid(validPerson);
		}
	}
	
	@Test
	public void testFirstnameNotNull(){
		validPerson.setFirstname(null);
		assertEquals(null, validPerson.getFirstname());
		assertInvalid(validPerson);
	}
	
	@Test
	public void testFirstnameNotEmpty(){
		validPerson.setFirstname("");
		assertEquals("", validPerson.getFirstname());
		assertInvalid(validPerson);
	}
	
	
	@Test
	public void testFirstnameNotLongerThan50(){
		assertEquals(50, Person.MAX_NAME_LENGTH);
		validPerson.setFirstname(stringUtil.getWithLength(Person.MAX_NAME_LENGTH+1) );
		assertInvalid(validPerson);
	}
	
	@Test
	public void testFirstnameCorrectValue(){
		String[] validValues= {"A","Abcsdafasd", "Name" , stringUtil.getWithLength(Person.MAX_NAME_LENGTH) };
		for(String s :validValues){
			validPerson.setFirstname(s);
			assertEquals(s, validPerson.getFirstname());
			assertValid(validPerson);
		}
	}
	
	
	@Test
	public void testSexNotNull() {
		validPerson.setSex(null);
		assertEquals(null, validPerson.getSex());
		assertInvalid(validPerson);
	}
	
	
	@Test
	public void testSexCorrectValue() {
		validPerson.setSex(Gender.MALE);
		assertEquals(Gender.MALE, validPerson.getSex());
		assertValid(validPerson);
		
		validPerson.setSex(Gender.FEMALE);
		assertEquals(Gender.FEMALE, validPerson.getSex());
		assertValid(validPerson);
	}
	

	@Test
	public void testLoginNull() {
		validPerson.setLogin(null);
		assertNull(validPerson.getTrialSite());
		assertValid(validPerson);
	}
	
	
	@Test
	public void testLoginCorrectValue() {
		Login login = factory.getLogin();
		validPerson.setLogin(login);
		assertEquals(login, validPerson.getLogin());
		assertValid(validPerson);
	}

	@Test
	public void testAssistantIsNull() {
		validPerson.setAssistant(null);
		assertEquals(null, validPerson.getAssistant());
		assertValid(validPerson);
	}
	
	@Test
	public void testAssistantWithoutLogin() {
		Person assistant = factory.getPerson();
		assistant.setLogin(null);
		validPerson.setAssistant(assistant);
		assertEquals(assistant, validPerson.getAssistant());
		assertValid(validPerson);
	}
	
	
	@Test
	public void testAssistantWithLogin() {
		Person assistant = factory.getPerson();
		assistant.setLogin(factory.getLogin());
		validPerson.setAssistant(assistant);
		assertEquals(assistant, validPerson.getAssistant());
		assertInvalid(validPerson);
	}

	@Test
	public void testTrialSiteNull() {
		validPerson.setTrialSite(null);
		assertNull(validPerson.getTrialSite());
		assertValid(validPerson);
	}
	
	
	@Test
	public void testTrialSiteCorrectValue() {
		TrialSite trialSite = factory.getTrialSite();
		validPerson.setTrialSite(trialSite);
		assertEquals(trialSite, validPerson.getTrialSite());
		assertValid(validPerson);
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
