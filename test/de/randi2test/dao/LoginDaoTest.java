package de.randi2test.dao;

import static de.randi2test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import org.hibernate.validator.InvalidStateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;
import de.randi2test.utility.DomainObjectFactory;
import de.randi2test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml" })
public class LoginDaoTest {

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private TestStringUtil testStringUtil;

	@Test
	public void CreateAndSaveTest() {

		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(Login.MAX_USERNAME_LENGTH));

		assertNotSaved(l);
		loginDao.save(l);
		assertSaved(l);

		assertNotNull(loginDao.get(l.getId()));

	}

	@Test
	public void GetUsernameTest() {
		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(10));
		loginDao.save(l);
		Login l2 = loginDao.get(l.getUsername());
		assertEquals(l.getId(), l2.getId());
		assertEquals(l.getUsername(), l2.getUsername());
	}

	@Test
	public void testSaveWithPerson() {
		Person validPerson = factory.getPerson();
		validPerson.setSurname(testStringUtil.getWithLength(20));
		validPerson.setFirstname(testStringUtil.getWithLength(20));
		validPerson.setEMail("abc@def.xy");
		validPerson.setGender(Gender.MALE);
		validPerson.setMobile("123456");
		validPerson.setPhone("123456");
		validPerson.setFax("123456");

		Login login = factory.getLogin();
		
		login.setPerson(validPerson);
		try {
			loginDao.save(login);
			fail("should throw exception");
		} catch (InvalidStateException e) {}
		 login.setUsername(testStringUtil.getWithLength(20));
		 loginDao.save(login);
	}

}
