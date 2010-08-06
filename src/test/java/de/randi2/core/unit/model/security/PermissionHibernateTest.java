package de.randi2.core.unit.model.security;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.springframework.security.acls.domain.AclFormattingUtils;

import de.randi2.model.security.PermissionHibernate;


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
