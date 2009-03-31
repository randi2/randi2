package de.randi2test.model.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.acls.AclFormattingUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static junit.framework.Assert.*;

import de.randi2.model.security.PermissionHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class PermissionHibernateTest {

	@Test
	public void testConstructor(){
		PermissionHibernate permission = new PermissionHibernate(32,'C');
		assertEquals(32, permission.getMask());
		assertEquals('C', permission.getCode());
	
	}
	
	@Test
	public void testPattern(){
		PermissionHibernate permission = new PermissionHibernate(32,'C');
		assertEquals(AclFormattingUtils.printBinary(32, 'C'), permission.getPattern());
	
	}
}
