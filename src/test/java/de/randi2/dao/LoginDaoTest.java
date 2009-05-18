package de.randi2.dao;

import static de.randi2.test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.validator.InvalidStateException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.LoginDao;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml" })
public class LoginDaoTest{

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private HibernateTemplate template;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private TestStringUtil testStringUtil;
	
	@Test
	public void createAndSaveTest() {

		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(Login.MIN_USERNAME_LENGTH) + "@xyz.com");

		assertNotSaved(l);
		loginDao.create(l);
		assertSaved(l);

		assertNotNull(loginDao.get(l.getId()));

	}

	@Test
	public void getUsernameTest() {
		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(10) + "@xyz.com");
		loginDao.create(l);
		Login l2 = loginDao.get(l.getUsername());
		assertEquals(l.getId(), l2.getId());
		assertEquals(l.getUsername(), l2.getUsername());
	}

	@Test
	public void testSaveWithPerson() {
		Person validPerson = factory.getPerson();

		Login login = factory.getLogin();

		login.setPerson(validPerson);
		login.setUsername("");
		try {
			loginDao.create(login);
			fail("should throw exception");
		} catch (InvalidStateException e) {
		}

		login = factory.getLogin();
		login.setUsername(testStringUtil.getWithLength(20)+ "@xyz.com");
		loginDao.create(login);
		assertFalse(login.getId() == AbstractDomainObject.NOT_YET_SAVED_ID);
		Login l = loginDao.get(login.getId());
		assertEquals(login.getId(), l.getId());
	}

//	@Test
	@Ignore
	public void testFindByExample() {
		Login l = factory.getLogin();
		loginDao.create(l);
		assertTrue(l.getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Login exampleLogin = new Login();
		exampleLogin.setUsername(l.getUsername());

		List<Login> list = loginDao.findByExample(exampleLogin);

		assertEquals(1, list.size());

		assertEquals(l.getId(), list.get(0).getId());

	}
	
	
}
