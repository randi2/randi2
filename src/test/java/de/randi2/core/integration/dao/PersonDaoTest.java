package de.randi2.core.integration.dao;

import static de.randi2.testUtility.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.testUtility.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.PersonDao;
import de.randi2.model.Person;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.TestStringUtil;

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
	public void testGetAll() {
		for (int i = 0; i < 100; i++) {
			dao.create(factory.getPerson());
		}
		assertTrue("getAll failed", dao.getAll().size() > 100);
	}
}
