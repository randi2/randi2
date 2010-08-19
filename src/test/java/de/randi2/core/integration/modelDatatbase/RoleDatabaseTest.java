package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Role;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

public class RoleDatabaseTest extends AbstractDomainDatabaseTest<Role> {

	public RoleDatabaseTest() {
		super(Role.class);
	}

	@Test
	@Transactional
	public void databaseIntegrationTest() {
		Role role = new Role(
				"ROLE_XYZ", false, true, true, true, false, false, true,
				true, false, true, false, true, false, true, true, false, true,
				false, true, false, true, true, false, true, false, false, false,
				false, null);
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
		sessionFactory.getCurrentSession().persist(role);
		assertTrue(role.getId()>0);
		
		Role dbRole = (Role) sessionFactory.getCurrentSession().get(Role.class, role.getId());
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
	
}
