package de.randi2.core.integration.dao;

import static de.randi2.testUtility.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.testUtility.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml" })
@Transactional
public class LoginDaoTest extends AbstractDaoTest{

	@Autowired
	private LoginDao loginDao;
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
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void getUsernameTest() {
		Login l = initUsernameTest();
		entityManager.clear();
		Login l2 = loginDao.get(l.getUsername());
		assertEquals(l.getId(), l2.getId());
		assertEquals(l.getUsername(), l2.getUsername());
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private Login initUsernameTest(){
		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(Login.MIN_USERNAME_LENGTH) + "@xyz.com");
		loginDao.create(l);
		return l;
	}

	
}
