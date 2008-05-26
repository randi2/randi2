package de.randi2test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import de.randi2.model.Role;
import de.randi2test.utility.AbstractDomainTest;

public class RoleTest extends AbstractDomainTest<Role>{

	private Role validRole;
	
	public RoleTest(){
		super(Role.class);
	}
	
	public void setUp(){
		validRole= factory.getRole();
	}
	
	@Test
	public void testConstructor(){
		Role r = new Role();
		
		assertEquals(0, r.getRights().size());
		assertEquals("", r.getName());
		
	}
}
