package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import de.randi2.model.security.PermissionHibernate;
import de.randi2.test.utility.AbstractDomainTest;

public class RoleTest extends AbstractDomainTest<Role> {

	
	public RoleTest(){
		super(Role.class);
	}
	
	@Test
	public void testConstructor(){
		Role role = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		
		assertEquals("ROLE_XYZ",role.getName());
		assertEquals(false, role.isCreateTrialSite() );
		assertEquals( true, role.isScopeTrialSiteView() );
		assertEquals( true, role.isReadTrialSite() );
		assertEquals( true, role.isScopeTrialSiteWrite() );
		assertEquals( false, role.isWriteTrialSite () );
		assertEquals( false, role.isAdminTrialSite () );
		assertEquals( true, role.isWriteOwnUser () );
		assertEquals( true, role.isReadOwnUser () );
		assertEquals( false, role.isAdminOwnUser() );
		assertEquals( true, role.isScopeUserCreate() ); 
		assertEquals( false, role.isCreateUser() );
		assertEquals( true, role.isScopeUserWrite () );
		assertEquals( false, role.isWriteOtherUser () );
		assertEquals( true, role.isScopeUserRead() );
		assertEquals( true, role.isReadOtherUser() );
		assertEquals( false, role.isAdminOtherUser() );
		assertEquals( true, role.isScopeTrialCreat() );
		assertEquals( false, role.isCreateTrial() );
		assertEquals( true, role.isScopeTrialWrite() );
		assertEquals( false, role.isWriteTrial () );
		assertEquals( true, role.isScopeTrialRead() ); 
		assertEquals( true, role.isReadTrial () );
		assertEquals( false, role.isAdminTrial() );
		assertEquals( true, role.isCreateTrialSubject () );
		assertEquals( false, role.isWriteTrialSubject() ); 
		assertEquals( false, role.isReadTrialSubject() ); 
		assertEquals( false, role.isAdminTrialSubject() ); 
		assertEquals( false, role.isCreateRole() );
		assertTrue(role.getRolesToAssign().isEmpty());
	}
	
	@Test
	public void testEqualsHashCode(){
		Role role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, true, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		Role role2 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		Role role3 = new Role(
				"ROLE_123", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, false, false, true, false, false, false,
				false, new ArrayList<Role>());
		
		assertEquals(role1, role2);
		assertEquals(role1.hashCode(), role2.hashCode());
		assertFalse(role1.hashCode() == role3.hashCode());
		assertFalse(role1.equals(role3));
		assertFalse(role1.equals("test"));
		assertFalse(role1.equals(null));	
	}
	
	@Test
	public void testGetOwnUserPermission(){
		Role role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, true, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(3, role1.getOwnUserPermissions().size());
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.READ));
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.WRITE));
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.ADMINISTRATION));
		
		role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(2, role1.getOwnUserPermissions().size());
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.READ));
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.WRITE));
		
		role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, false,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(1, role1.getOwnUserPermissions().size());
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.READ));
		
		role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				false, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(1, role1.getOwnUserPermissions().size());
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.WRITE));
	
		role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, false,
				false, true, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(1, role1.getOwnUserPermissions().size());
		assertTrue(role1.getOwnUserPermissions().contains(PermissionHibernate.ADMINISTRATION));
		
	}
	
	@Test
	public void testGetTrialSitePermission(){
		Role role1 = new Role(
				"ROLE_XYZ", false, true, true, true, true, true, true,
				true, true, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(3, role1.getTrialSitePermissions().size());
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.READ));
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.WRITE));
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.ADMINISTRATION));
		
		role1 = new Role(
				"ROLE_XYZ", false, true, true, true, true, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(2, role1.getTrialSitePermissions().size());
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.READ));
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.WRITE));
		
		role1 = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, false,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(1, role1.getTrialSitePermissions().size());
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.READ));
		
		role1 = new Role(
				"ROLE_XYZ", false, true, false, true, true, false, true,
				false, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(1, role1.getTrialSitePermissions().size());
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.WRITE));
	
		role1 = new Role(
				"ROLE_XYZ", false, true, false, true, false, true, false,
				false, true, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals(1, role1.getTrialSitePermissions().size());
		assertTrue(role1.getTrialSitePermissions().contains(PermissionHibernate.ADMINISTRATION));
	}
	
	@Test
	public void databaseIntegrationTest() {
		Role role = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		 role = new Role();
		role.setName("ROLE_XYZ");
		role.setCreateTrialSite(false);
		role.setScopeTrialSiteView(true);
		role.setReadTrialSite(true);
		role.setScopeTrialSiteWrite(true);
		role.setWriteTrialSite(false);
		role.setAdminTrialSite(false);
		role.setWriteOwnUser(true);
		role.setReadOwnUser(true);
		role.setAdminOwnUser(false);
		role.setScopeUserCreate(true);
		role.setCreateUser(false);
		role.setScopeUserWrite(true);
		role.setWriteOtherUser(true);
		role.setScopeUserRead(false);
		role.setReadOtherUser(true);
		role.setAdminOtherUser(true);
		role.setScopeTrialCreat(false);
		role.setCreateTrial(true);
		role.setScopeTrialWrite(false);
		role.setWriteTrial(true);
		role.setScopeTrialRead(false);
		role.setReadTrial(true);
		role.setAdminTrial(true);
		role.setCreateTrialSubject(false);
		role.setWriteTrialSubject(true);
		role.setReadTrialSubject(false);
		role.setAdminTrialSubject(false);
		role.setCreateRole(false);
		role.setScopeTrialSiteView(true);
		hibernateTemplate.persist(role);
		assertTrue(role.getId()>0);
		
		Role dbRole = (Role) hibernateTemplate.get(Role.class, role.getId());
		assertEquals("ROLE_XYZ",dbRole.getName());
		assertEquals(false, dbRole.isCreateTrialSite() );
		assertEquals( true, dbRole.isScopeTrialSiteView() );
		assertEquals( true, dbRole.isReadTrialSite() );
		assertEquals( true, dbRole.isScopeTrialSiteWrite() );
		assertEquals( false, dbRole.isWriteTrialSite () );
		assertEquals( false, dbRole.isAdminTrialSite () );
		assertEquals( true, dbRole.isWriteOwnUser () );
		assertEquals( true, dbRole.isReadOwnUser () );
		assertEquals( false, dbRole.isAdminOwnUser() );
		assertEquals( true, dbRole.isScopeUserCreate() ); 
		assertEquals( false, dbRole.isCreateUser() );
		assertEquals( true, dbRole.isScopeUserWrite () );
		assertEquals( true, dbRole.isWriteOtherUser () );
		assertEquals( false, dbRole.isScopeUserRead() );
		assertEquals( true, dbRole.isReadOtherUser() );
		assertEquals( true, dbRole.isAdminOtherUser() );
		assertEquals( false, dbRole.isScopeTrialCreat() );
		assertEquals( true, dbRole.isCreateTrial() );
		assertEquals( false, dbRole.isScopeTrialWrite() );
		assertEquals( true, dbRole.isWriteTrial () );
		assertEquals( false, dbRole.isScopeTrialRead() ); 
		assertEquals( true, dbRole.isReadTrial () );
		assertEquals( true, dbRole.isAdminTrial() );
		assertEquals( false, dbRole.isCreateTrialSubject () );
		assertEquals( true, dbRole.isWriteTrialSubject() ); 
		assertEquals( false, dbRole.isReadTrialSubject() ); 
		assertEquals( false, dbRole.isAdminTrialSubject() ); 
		assertEquals( false, dbRole.isCreateRole() );
		assertEquals( true, dbRole.isScopeTrialSiteView() );
		
	}
	
	@Test
	public void testGetRequieredFields(){
		Role role = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		Map<String, Boolean> map = role.getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("name")) assertTrue(key,map.get(key)); else
			assertFalse(key,map.get(key));
		}
	}
	
	@Test
	public void testUiName(){
		Role role = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, new ArrayList<Role>());
		assertEquals("ROLE_XYZ", role.getUIName());
	}
}
		