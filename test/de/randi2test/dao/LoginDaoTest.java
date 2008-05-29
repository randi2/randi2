package de.randi2test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static de.randi2test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2test.utility.DomainObjectFactory;
import de.randi2test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml"})
public class LoginDaoTest {

	@Autowired private LoginDao loginDao;	
	@Autowired private DomainObjectFactory factory;
	@Autowired private TestStringUtil testStringUtil;
	
	@Test
	public void CreateAndSaveTest(){
		
		Login l = factory.getLogin();
		
		assertNotSaved(l);
		loginDao.save(l);
		assertSaved(l);
		
		assertNotNull(loginDao.get(l.getId()));
		
		
	}
	
	@Test
	public void GetUsernameTest(){
		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(10));
		loginDao.save(l);
		Login l2= loginDao.get(l.getUsername());
		assertEquals(l.getId(), l2.getId());
		assertEquals(l.getUsername(), l2.getUsername());
	}
	
}
