package de.randi2test.dao;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml"})
@Transactional
public class LoginDaoTest {

	@Autowired private LoginDao loginDao;	
	@Autowired private DomainObjectFactory factory;
	
	@Test
	public void CreateAndSaveTest(){
		
		Login l = factory.getLogin();
		
		assertNotSaved(l);
		loginDao.save(l);
		assertSaved(l);
		
		assertNotNull(loginDao.get(l.getId()));
		
		
	}
	
}
