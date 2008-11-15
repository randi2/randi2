package de.randi2test.dao;

import static de.randi2test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
import de.randi2test.utility.DomainObjectFactory;
import de.randi2test.utility.TestStringUtil;

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
	public void CreateAndSaveTest(){
		
		
		Person p = factory.getPerson();
		p.setSurname("test");
		
		assertNotSaved(p);
		dao.save(p);
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
		dao.save(validPerson);
		validPerson.setLogin(login);

		login.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH + 1));
		try{
			dao.save(validPerson);
			fail("should throw exception");
		}catch (InvalidStateException e) {}
	}
}
