package de.randi2.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import de.randi2.model.User;
import de.randi2.model.User.Titel;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/de/randi2/applicationContext.xml"})
@Transactional
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	
	
	@Test
	public void testCreateAndSave(){
		User u = new User();
		u.setAName("Mustermann");
		u.setAEmail("mustemann@xyz.de");
		u.setATelefonnumber("12345678");
		u.setATitel(Titel.KEIN_TITEL);
		u.setAPassword("geheim");
		u.setAGender('m');
		
		userDao.save(u);
		
		User u2 = userDao.get(u.getId());
		
		assertNotNull(u2);
		assertEquals(u.getAName(), u2.getAName());
	}

}
