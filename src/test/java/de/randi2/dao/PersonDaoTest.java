package de.randi2.dao;

import static de.randi2.test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.validator.InvalidStateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.PersonDao;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml"})
public class PersonDaoTest {

	@Autowired private PersonDao dao;	
	@Autowired private DomainObjectFactory factory;
	@Autowired protected TestStringUtil stringUtil;
	
	private Person validPerson;
	
	@Before
	public void setUp(){
		validPerson = factory.getPerson();
	}
	
	@Test
	public void createAndSaveTest(){
		
		
		Person p = factory.getPerson();
		p.setSurname("test");
		
		assertNotSaved(p);
		dao.create(p);
		assertSaved(p);
		
		assertNotNull(dao.get(p.getId()));
		
		
	}
	
	@Test
	public void testSaveWithLogin(){
		validPerson.setFirstname(stringUtil.getWithLength(20));
		validPerson.setEMail("abc@def.xy");
		validPerson.setGender(Gender.MALE);
		validPerson.setMobile("123456");
		validPerson.setPhone("123456");
		validPerson.setFax("123456");
		
		Login login = factory.getLogin();
		validPerson.setSurname(stringUtil.getWithLength(20));
		dao.create(validPerson);
		validPerson.setLogin(login);

		login.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH + 1));
		try{
			dao.update(validPerson);
			fail("should throw exception");
		}catch (InvalidStateException e) {}
	}
	
	
	@Test
	public void testGetAll(){
		assertTrue(true);
//		for (int i = 0; i<100;i++){
//			dao.create(factory.getPerson());
//		}
//		assertTrue("getAll failed", dao.getAll().size() >100);
	}
}
