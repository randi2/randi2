package de.randi2.services;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml", "/META-INF/subconfig/security.xml"})
public class UserServiceTest {


	
	@Test
	public void test(){
		assertTrue(true);
	}
}
