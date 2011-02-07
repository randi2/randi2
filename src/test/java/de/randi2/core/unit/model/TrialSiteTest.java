package de.randi2.core.unit.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class TrialSiteTest extends AbstractDomainTest<TrialSite> {

	private TrialSite validTrialSite;

	public TrialSiteTest() {
		super(TrialSite.class);
	}

	@Before
	public void setUp() {
		validTrialSite = factory.getTrialSite();
	}

	@Test
	public void testConstuctor() {
		TrialSite c = new TrialSite();
		assertEquals("", c.getName());
		assertEquals("", c.getStreet());
		assertEquals("", c.getPostcode());
		assertEquals("", c.getCity());
	}
	
	
	@Test
	public void testCityNotNull() {
		 validTrialSite.setCity(null);
		 assertEquals(null, validTrialSite.getCity());
		 assertInvalid(validTrialSite);
	}
	
	@Test
	public void testCityNotLongerThan255() {
		String[] invalidValues = { stringUtil.getWithLength(256),
				stringUtil.getWithLength(650) };
		for (String s : invalidValues) {
			validTrialSite.setCity(s);
			 assertEquals(s, validTrialSite.getCity());
			 assertInvalid(validTrialSite);
		}
	}
	
	@Test
	public void testCityCorrect() {
		String[] validValues = { stringUtil.getWithLength(255),
				stringUtil.getWithLength(20), "New York", "Heidelberg"  };
		for (String s : validValues) {
			validTrialSite.setCity(s);
			 assertEquals(s, validTrialSite.getCity());
			 assertValid(validTrialSite);
		}
	}
	
	@Test
	public void testContactPersonNotNull() {
		validTrialSite.setContactPerson(null);
		assertEquals(null, validTrialSite.getContactPerson());
		assertInvalid(validTrialSite);
	}
	
	@Test
	public void testContactPersonWithLoginInvalid() {
		Login l = factory.getLogin();
		validTrialSite.setContactPerson(l.getPerson());
		assertEquals(l.getPerson(), validTrialSite.getContactPerson());
		assertInvalid(validTrialSite);
	}

	
	@Test
	public void testContactPersonCorrect() {
		Person p = factory.getPerson();
		validTrialSite.setContactPerson(p);
		assertEquals(p, validTrialSite.getContactPerson());
		assertValid(validTrialSite);
	}

	
	@Test
	public void testCountryNull() {
		validTrialSite.setCountry(null);
		assertEquals(null, validTrialSite.getCountry());
		assertValid(validTrialSite);
	}
	
	@Test
	public void testCountryEmpty() {
		validTrialSite.setCountry("");
		assertEquals("", validTrialSite.getCountry());
		assertValid(validTrialSite);
	}
	
	@Test
	public void testCountryNotLongerThan255() {
		String[] invalidValues = { stringUtil.getWithLength(256),
				stringUtil.getWithLength(650) };
		for (String s : invalidValues) {
			validTrialSite.setCountry(s);
			assertEquals(s, validTrialSite.getCountry());
			 assertInvalid(validTrialSite);
		}
		
	}
	
	@Test
	public void testCountryCorrect() {
		String[] validValues = { stringUtil.getWithLength(255),
				stringUtil.getWithLength(1), stringUtil.getWithLength(20), "Germany", "UK" };
		for (String s : validValues) {
			validTrialSite.setCountry(s);
			assertEquals(s, validTrialSite.getCountry());
			 assertValid(validTrialSite);
		}
	}



	@Test
	public void testMembersNull() {
		validTrialSite.setMembers(null);
		assertNull(validTrialSite.getMembers());
		assertValid(validTrialSite);
	}
	
	@Test
	public void testMembersCorrect() {

		List<Person> members = new ArrayList<Person>();

		for (int i = 0; i < 100; i++) {
			Person p = factory.getPerson();
			validTrialSite.getMembers().add(p);
			members.add(p);
		}
		validTrialSite.setMembers(members);
		assertEquals(members.size(), validTrialSite.getMembers().size());
		assertValid(validTrialSite);
	}
	
	@Test
	public void testNameNotNull() {
		validTrialSite.setName(null);
		assertNull(validTrialSite.getName());
		assertInvalid(validTrialSite);
	}
	
	@Test
	public void testNameNotEmpty() {
		validTrialSite.setName("");
		assertEquals("", validTrialSite.getName());
		assertInvalid(validTrialSite);
	}
	
	@Test
	public void testNameNotLongerThan255() {
		String[] invalidValues = { stringUtil.getWithLength(256),
				stringUtil.getWithLength(480)};
		for (String s : invalidValues) {
			validTrialSite.setName(s);
			assertEquals(s, validTrialSite.getName());
			 assertInvalid(validTrialSite);
		}
	}
	
	@Test
	public void testNameCorrect() {
		String[] validValues = { stringUtil.getWithLength(255),
				stringUtil.getWithLength(25), "Trial site 1"};
		for (String s : validValues) {
			validTrialSite.setName(s);
			assertEquals(s, validTrialSite.getName());
			 assertValid(validTrialSite);
		}
	}
	
	@Test
	public void testPasswordNotNull(){
		validTrialSite.setPassword(null);
		assertEquals(null, validTrialSite.getPassword());
		assertInvalid(validTrialSite);
	}
	
	@Test
	public void testPasswordNotEmpty(){
		validTrialSite.setPassword("");
		assertEquals("", validTrialSite.getPassword());
		assertInvalid(validTrialSite);
	}
	
	
	@Test
	public void testPasswordNotShorterThan8(){
		String[] invalidPasswords = {"ecet0$s","sad.a", stringUtil.getWithLength(5)+",3", null, ""};
		for (String s: invalidPasswords){
			validTrialSite.setPassword(s);
			assertEquals(s, validTrialSite.getPassword());
			assertInvalid(validTrialSite,s);
		}
	}
	
	@Test
	public void testPasswordNotLongerThan30(){
		String[] invalidPasswords = {stringUtil.getWithLength(30)+ "$1", stringUtil.getWithLength(28)+"$t3"};
		for (String s: invalidPasswords){
			validTrialSite.setPassword(s);
			assertEquals(s, validTrialSite.getPassword());
			assertInvalid(validTrialSite, s);
		}
	}
	
	@Test
	public void testPasswordLengthHashedValueEquals64(){
		String[] validPasswords = {stringUtil.getWithLength(64)};
		for (String s: validPasswords){
			validTrialSite.setPassword(s);
			assertEquals(s, validTrialSite.getPassword());
			assertValid(validTrialSite);
		}
	}
	
	@Test
	public void testPasswordLengthHashedValueUnequals64(){
		String[] invalidPasswords = {stringUtil.getWithLength(65), stringUtil.getWithLength(150), stringUtil.getWithLength(65), stringUtil.getWithLength(63), stringUtil.getWithLength(34), stringUtil.getWithLength(20)};
		for (String s: invalidPasswords){
			validTrialSite.setPassword(s);
			assertEquals(s, validTrialSite.getPassword());
			assertInvalid(validTrialSite, s);
		}
	}
	
	
	@Test
	public void testPasswordWithCorrectLengthAndWithoutSpecialSign(){
		String[] invalidPasswords = {"secret0secret","sad.alhljhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+"z2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+"h3"};
		for (String s: invalidPasswords){
			validTrialSite.setPassword(s);
			assertEquals(s, validTrialSite.getPassword());
			assertInvalid(validTrialSite,s);
		}
	}
	
	@Test
	public void testPasswordWithCorrectLengthAndSpecialSigns(){
		String[] validPasswords = {"secret0$secret","sad.alhl3jhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+"/3"};
		for (String s: validPasswords){
			validTrialSite.setPassword(s);
			assertEquals(s, validTrialSite.getPassword());
			assertValid(validTrialSite);
		}
	}
	
	@Test
	public void testPostcodeNotNull() {
		validTrialSite.setPostcode(null);
		assertEquals(null, validTrialSite.getPostcode());
		assertInvalid(validTrialSite);

	}
	
	@Test
	public void testPostcodeNotLongerThan10() {
		String[] invalidPostcode = {stringUtil.getWithLength(11),stringUtil.getWithLength(365)};
		for (String s: invalidPostcode){
			validTrialSite.setPostcode(s);
			assertEquals(s, validTrialSite.getPostcode());
			assertInvalid(validTrialSite, s);
		}
	}
	
	@Test
	public void testPostcodeCorrect() {
		String[] validPostcode = {stringUtil.getWithLength(10),stringUtil.getWithLength(5), ""};
		for (String s: validPostcode){
			validTrialSite.setPostcode(s);
			assertEquals(s, validTrialSite.getPostcode());
			assertValid(validTrialSite);
		}
	}

	
	@Test
	public void testStreetNotNull() {
		validTrialSite.setStreet(null);
		assertEquals(null, validTrialSite.getStreet());
		assertInvalid(validTrialSite);

	}
	
	@Test
	public void testStreetNotLongerThan255() {
		String[] invalidStreet = {stringUtil.getWithLength(256),stringUtil.getWithLength(365)};
		for (String s: invalidStreet){
			validTrialSite.setStreet(s);
			assertEquals(s, validTrialSite.getStreet());
			assertInvalid(validTrialSite, s);
		}
	}
	
	@Test
	public void testStreetCorrect() {
		String[] validSteet = {stringUtil.getWithLength(255),stringUtil.getWithLength(5), ""};
		for (String s: validSteet){
			validTrialSite.setStreet(s);
			assertEquals(s, validTrialSite.getStreet());
			assertValid(validTrialSite);
		}
	}
	
	@Test
	public void testTrialsNull() {
		validTrialSite.setTrials(null);
		assertEquals(null, validTrialSite.getTrials());
		assertValid(validTrialSite);
	}
	
	@Test
	public void testTrialsCorrect() {
		List<Trial> tl = new ArrayList<Trial>();

		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		validTrialSite.setTrials(tl);

		assertEquals(tl.size(), validTrialSite.getTrials().size());
		assertValid(validTrialSite);
		
		List<Trial> trials = new ArrayList<Trial>();
		trials.add(new Trial());
		validTrialSite.setTrials(trials);
		assertEquals(trials, validTrialSite.getTrials());
		assertValid(validTrialSite);
	}

	@Test
	public void testGetMembersWithSpecifiedRole() {
		List<Person> members = new ArrayList<Person>();
		Login l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());

		l = factory.getLogin();
		l.addRole(Role.ROLE_USER);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_USER);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_P_INVESTIGATOR);
		members.add(l.getPerson());

		validTrialSite.setMembers(members);

		List<Login> logins = validTrialSite
				.getMembersWithSpecifiedRole(Role.ROLE_USER);
		assertEquals(members.size(), logins.size());

		logins = validTrialSite.getMembersWithSpecifiedRole(Role.ROLE_ADMIN);
		assertEquals(4, logins.size());
		for (Person p : members.subList(0, 3)) {
			assertTrue(logins.contains(p.getLogin()));
		}
		logins = validTrialSite
				.getMembersWithSpecifiedRole(Role.ROLE_P_INVESTIGATOR);
		assertEquals(1, logins.size());
		assertTrue(logins.contains(members.get(6).getLogin()));
	}

	@Test
	public void testGetRequieredFields() {
		Map<String, Boolean> map = validTrialSite.getRequiredFields();
		for (String key : map.keySet()) {
			if (key.equals("name")) {
				assertTrue(map.get(key));
			} else if (key.equals("street")) {
				assertTrue(map.get(key));
			} else if (key.equals("postcode")) {
				assertTrue(map.get(key));
			} else if (key.equals("city")) {
				assertTrue(map.get(key));
			} else if (key.equals("country")) {
				assertFalse(map.get(key));
			} else if (key.equals("password")) {
				assertTrue(map.get(key));
			} else if (key.equals("contactPerson")) {
				assertTrue(map.get(key));
			} else if (key.equals("members")) {
				assertFalse(map.get(key));
			} else if (key.equals("trials")) {
				assertFalse(map.get(key));
			} else if (key.equals("MAX_LENGTH_POSTCODE")) {
				assertFalse(map.get(key));
			} else if (key.equals("serialVersionUID")) {
				assertFalse(map.get(key));
			} else if (key.equals("$VRc")) {
				assertFalse(map.get(key));
			} else
				fail(key + " not checked");
		}
	}

	@Test
	public void testEqualsHashCode() {
		TrialSite trialSite1 = new TrialSite();
		TrialSite trialSite2 = new TrialSite();
		trialSite1.setId(0);
		trialSite2.setId(0);
		trialSite1.setVersion(0);
		trialSite2.setVersion(0);
		assertEquals(trialSite1, trialSite2);
		assertEquals(trialSite1.hashCode(), trialSite2.hashCode());
		trialSite1.setId(1);

		assertFalse(trialSite1.equals(trialSite2));
		trialSite1.setId(0);
		assertEquals(trialSite1, trialSite2);
		assertEquals(trialSite1.hashCode(), trialSite2.hashCode());

		trialSite1.setVersion(1);
		assertFalse(trialSite1.equals(trialSite2));
		trialSite1.setVersion(0);
		assertEquals(trialSite1, trialSite2);
		assertEquals(trialSite1.hashCode(), trialSite2.hashCode());

		trialSite1.setName("test");
		assertFalse(trialSite1.equals(trialSite2));
		trialSite2.setName("test");
		assertEquals(trialSite1, trialSite2);
		assertEquals(trialSite1.hashCode(), trialSite2.hashCode());

		assertFalse(trialSite1.equals(null));
		assertFalse(trialSite1.equals(new TreatmentArm()));
	}

	@Test
	public void testToString() {
		assertNotNull(validTrialSite.toString());
	}

	@Test
	public void testUiName() {
		validTrialSite.setName("name trial site");
		assertEquals("name trial site", validTrialSite.getUIName());
	}

}
