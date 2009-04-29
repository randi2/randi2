package de.randi2.dao;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml", "/META-INF/subconfig/security.xml"})
public class RoleDaoTest {

	@Test
	public void test(){
		assertTrue(true);
	}
}
