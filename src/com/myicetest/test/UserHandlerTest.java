package com.myicetest.test;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myicetest.dao.UserDao;
import com.myicetest.handlers.UserHandler;
import com.myicetest.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/myicetest/META-INF/spring.xml"})
public class UserHandlerTest {


	@Autowired
	private UserHandler userHandler;
	@Autowired
	private UserDao userDao;

	@Test
	public void testCreateAndSave() {
		User u1 = new User();

		u1.setFirstname("testFirstname");
		u1.setPassword("testPass");
		u1.setSurname("testSurname");
		u1.setLoginname("loginName");

		userHandler.setUser(u1);
		userHandler.saveUser();

		User u2 = userDao.get(u1.getId());
		assertNotNull(u2);
		//assertEquals(u1.getName(), u2.getName());
		//assertEquals(u1.getDescription(), u2.getDescription());
	}
}
