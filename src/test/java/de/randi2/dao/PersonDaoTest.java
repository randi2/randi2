package de.randi2.dao;

import static de.randi2.test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import liquibase.FileSystemFileOpener;
import liquibase.Liquibase;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.validator.InvalidStateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml" })
@Transactional
public class PersonDaoTest extends AbstractDaoTest{

	@Autowired
	private PersonDao dao;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	protected TestStringUtil stringUtil;

	@Autowired
	private DataSource dataSource;

	private Person validPerson;

	@Before
	public void setUp() {
		super.setUp();
		validPerson = factory.getPerson();
	}

	@Test
	public void createAndSaveTest() {

		Person p = factory.getPerson();
		p.setSurname("test");

		assertNotSaved(p);
		dao.create(p);
		assertSaved(p);

		assertNotNull(dao.get(p.getId()));

	}

	@Test
	public void testSaveWithLogin() {
		validPerson.setFirstname(stringUtil.getWithLength(20));
		validPerson.setEmail("abc@def.xy");
		validPerson.setSex(Gender.MALE);
		validPerson.setMobile("123456");
		validPerson.setPhone("123456");
		validPerson.setFax("123456");

		Login login = factory.getLogin();
		validPerson.setSurname(stringUtil.getWithLength(20));
		dao.create(validPerson);
		validPerson.setLogin(login);

		login.setUsername(stringUtil
				.getWithLength(Login.MAX_USERNAME_LENGTH + 1));
		try {
			dao.update(validPerson);
			fail("should throw exception");
		} catch (InvalidStateException e) {
		}
	}

	@Test
	public void testGetAll() {
		for (int i = 0; i < 100; i++) {
			dao.create(factory.getPerson());
		}
		assertTrue("getAll failed", dao.getAll().size() > 100);
	}
}
