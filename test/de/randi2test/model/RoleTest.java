package de.randi2test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Right;
import de.randi2.model.Role;
import de.randi2test.utility.AbstractDomainTest;

public class RoleTest extends AbstractDomainTest<Role>{

	private Role validRole;
	
	public RoleTest(){
		super(Role.class);
	}
	
	@Before
	public void setUp(){
		validRole= factory.getRole();
	}
	
	@Test
	public void testConstructor(){
		Role r = new Role();
		
		assertEquals(0, r.getRights().size());
	//	assertEquals("", r.getAuthority());
		
	}
	
	@Test
	public void testRights(){
		List<Right> rights = new ArrayList<Right>();
		rights.add(Right.EDIT_TRIAL);
		validRole.setRights(rights);
		
		assertTrue(rights.get(0)==validRole.getRights().get(0));
		
		hibernateTemplate.saveOrUpdate(validRole);
		
		assertTrue(validRole.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		
		Role r;
		r = (Role) hibernateTemplate.get(Role.class, validRole.getId());
		
		assertNotNull(r);
		assertEquals(validRole.getId(), r.getId());
		
		assertEquals(validRole.getRights().size(),r.getRights().size());
		
		List<Right> r2 = new ArrayList<Right>();
		
		validRole.setRights(r2);
		hibernateTemplate.saveOrUpdate(validRole);
		
		assertTrue(validRole.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		
		r = (Role) hibernateTemplate.get(Role.class, validRole.getId());
		
		assertNotNull(r);
		assertEquals(validRole.getId(), r.getId());
		assertEquals(0, validRole.getRights().size());
		assertEquals(validRole.getRights().size(),r.getRights().size());
		
		
	}
}
