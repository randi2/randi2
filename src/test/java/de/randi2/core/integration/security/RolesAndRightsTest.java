package de.randi2.core.integration.security;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Sid;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.HibernateAclService;
import de.randi2.dao.LoginDao;
import de.randi2.dao.RoleDao;
import de.randi2.dao.TrialSiteDao;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.SidHibernate;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.InitializeDatabaseUtil;
import de.randi2.utility.security.RolesAndRights;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml",
		"/META-INF/subconfig/security.xml" })
@Transactional
public class RolesAndRightsTest {

	@Autowired
	private InitializeDatabaseUtil databaseUtil;
	@Autowired
	private HibernateAclService aclService;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private RolesAndRights rolesAndRights;
	@Autowired
	private TrialSiteDao siteDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private RoleDao roleDao;

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Before
	@SuppressWarnings("unchecked")
	public void init() {
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// entityManager.persist(Role.ROLE_ADMIN);
		// entityManager.persist(Role.ROLE_ANONYMOUS);
		// entityManager.persist(Role.ROLE_INVESTIGATOR);
		// entityManager.persist(Role.ROLE_MONITOR);
		// entityManager.persist(
		// Role.ROLE_P_INVESTIGATOR);
		// entityManager.persist(Role.ROLE_STATISTICAN);
		// entityManager.persist(Role.ROLE_USER);
		//
		// aclService.createAclwithPermissions(new Login(), Role.ROLE_ANONYMOUS
		// .getName(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// Role.ROLE_ANONYMOUS.getName());
		// aclService.createAclwithPermissions(new Person(), Role.ROLE_ANONYMOUS
		// .getName(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// Role.ROLE_ANONYMOUS.getName());
		//
		//
		// List<Role> roles =
		// entityManager.createQuery("from Role").getResultList();
		// assertTrue(roles.contains(Role.ROLE_ADMIN));
		// assertTrue(roles.contains(Role.ROLE_ANONYMOUS));
		// assertTrue(roles.contains(Role.ROLE_INVESTIGATOR));
		// assertTrue(roles.contains(Role.ROLE_MONITOR));
		// assertTrue(roles.contains(Role.ROLE_P_INVESTIGATOR));
		// assertTrue(roles.contains(Role.ROLE_STATISTICAN));
		// assertTrue(roles.contains(Role.ROLE_USER));
		//
		// List<AclHibernate> acls =
		// entityManager.createQuery("select acl from AclHibernate acl, SidHibernate sid where acl.owner.sidname= ?").setParameter(1,
		// Role.ROLE_ANONYMOUS.getName()).getResultList();
		// boolean hasRightLogin = false;
		// boolean hasRightPerson = false;
		// for(AclHibernate acl: acls){
		// if
		// (acl.getObjectIdentity().getIdentifier()==AbstractDomainObject.NOT_YET_SAVED_ID){
		// if(acl.getObjectIdentity().getType().equals(Login.class.getCanonicalName())){
		// hasRightLogin = true;
		// }else
		// if(acl.getObjectIdentity().getType().equals(Person.class.getCanonicalName())){
		// hasRightPerson = true;
		// }
		// }
		// }
		// assertTrue(hasRightLogin);
		// assertTrue(hasRightPerson);
	}

	@Test
	public void grantRightsTrialSiteObjectAnonymousRoleTest() {
		TrialSite site = factory.getTrialSite();
		site.setId(1);
		Sid sid = new PrincipalSid(Role.ROLE_ANONYMOUS.getName());

		rolesAndRights.grantRights(site, null);

		assertEquals(1, entityManager.createQuery("from AclHibernate")
				.getResultList().size());

		Acl acl = aclService.readAclById(
				new ObjectIdentityHibernate(site.getClass(), site.getId()),
				Arrays.asList(sid));
		assertEquals(site.getId(), acl.getObjectIdentity().getIdentifier());
		assertEquals(site.getClass().getCanonicalName(), acl
				.getObjectIdentity().getType());

		List<AccessControlEntry> entries = acl.getEntries();
		assertEquals(1, entries.size());
		assertEquals('R',
				((PermissionHibernate) entries.get(0).getPermission())
						.getCode());
	}

	@Test
	public void grantRightsTrialSiteObjectRoleWithAdminTrialSiteTest() {
		Role role = new Role("ROLE_Name", false, false, false, false, false,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		TrialSite site = factory.getTrialSite();
		site.setId(1);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			// Anonymous role is tested with other test method
			if (!((SidHibernate) acl.getOwner()).getSidname().equals(
					Role.ROLE_ANONYMOUS.getName())) {
				assertEquals(site.getId(), acl.getObjectIdentity()
						.getIdentifier());
				assertEquals(site.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('A', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			}
		}
	}

	@Test
	public void grantRightsTrialSiteObjectRoleWithWriteTrialSiteTest() {
		TrialSite site = factory.getTrialSite();
		site.setId(1);

		// test without trial site scope
		Role role = new Role("ROLE_Name", false, false, false, false, true,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			// Anonymous role is tested with other test method
			if (!((SidHibernate) acl.getOwner()).getSidname().equals(
					Role.ROLE_ANONYMOUS.getName())) {
				assertEquals(site.getId(), acl.getObjectIdentity()
						.getIdentifier());
				assertEquals(site.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('W', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			}
		}
	}

	@Test
	public void grantRightsTrialSiteObjectRoleWithWriteTrialSiteScopeTest() {
		TrialSite site = factory.getTrialSite();
		site.setId(1);

		// test with trial site scope
		Role role_nr = new Role("ROLE_Name_nr", false, false, false, true,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, null);
		roleDao.create(role_nr);

		Login login_nr = factory.getLogin();
		login_nr.getRoles().add(role_nr);
		loginDao.create(login_nr);

		Sid sid_nr = new PrincipalSid(login_nr.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls_nr = entityManager.createQuery(
				"from AclHibernate").getResultList();
		assertEquals(1, acls_nr.size());
		assertTrue(((SidHibernate) acls_nr.get(0).getOwner()).getSidname()
				.equals(Role.ROLE_ANONYMOUS.getName()));

		Role role = new Role("ROLE_Name", false, false, false, true, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());
		assertTrue(((SidHibernate) acls.get(0).getOwner()).getSidname().equals(
				Role.ROLE_ANONYMOUS.getName()));
	}

	@Test
	public void grantRightsTrialSiteObjectRoleWithReadTrialSiteTest() {
		TrialSite site = factory.getTrialSite();
		site.setId(1);

		Role role = new Role("ROLE_Name", false, false, true, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			// Anonymous role is tested with other test method
			if (!((SidHibernate) acl.getOwner()).getSidname().equals(
					Role.ROLE_ANONYMOUS.getName())) {
				assertEquals(site.getId(), acl.getObjectIdentity()
						.getIdentifier());
				assertEquals(site.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('R', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			}
		}
	}

	@Test
	public void grantRightsTrialSiteObjectRoleWithReadTrialSiteScopeTest() {
		TrialSite site = factory.getTrialSite();
		site.setId(1);

		// test with trial site scope
		Role role_nr = new Role("ROLE_Name_nr", false, true, true, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, null);
		roleDao.create(role_nr);

		Login login_nr = factory.getLogin();
		login_nr.getRoles().add(role_nr);
		loginDao.create(login_nr);

		Sid sid_nr = new PrincipalSid(login_nr.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls_nr = entityManager.createQuery(
				"from AclHibernate").getResultList();
		assertEquals(1, acls_nr.size());
		assertTrue(((SidHibernate) acls_nr.get(0).getOwner()).getSidname()
				.equals(Role.ROLE_ANONYMOUS.getName()));

		Role role = new Role("ROLE_Name", false, true, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());
		assertTrue(((SidHibernate) acls.get(0).getOwner()).getSidname().equals(
				Role.ROLE_ANONYMOUS.getName()));
	}

	@Test
	public void grantRightsTrialSiteObjectRoleWithAllTrialSiteTest() {
		TrialSite site = factory.getTrialSite();
		site.setId(1);

		Role role = new Role("ROLE_Name", false, false, true, false, true,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(site, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			// Anonymous role is tested with other test method
			if (!((SidHibernate) acl.getOwner()).getSidname().equals(
					Role.ROLE_ANONYMOUS.getName())) {
				assertEquals(site.getId(), acl.getObjectIdentity()
						.getIdentifier());
				assertEquals(site.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(3, entries.size());
				List<Character> permissions = new ArrayList<Character>();
				permissions.add('R');
				permissions.add('W');
				permissions.add('A');
				for (AccessControlEntry ace : acl.getEntries()) {
					assertTrue(permissions.contains(((PermissionHibernate) ace
							.getPermission()).getCode()));
					assertTrue(permissions
							.remove((Character) ((PermissionHibernate) ace
									.getPermission()).getCode()));
				}
			}
		}
	}

	@Test
	public void grantRightsTrialObjectWithOutScopeAdminTest() {
		Trial trial = factory.getTrial();
		trial.setId(1);

		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, true,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());

		for (Acl acl : acls) {
			assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());
			assertEquals(trial.getClass().getCanonicalName(), acl
					.getObjectIdentity().getType());

			List<AccessControlEntry> entries = acl.getEntries();
			assertEquals(1, entries.size());
			assertEquals('A', ((PermissionHibernate) entries.get(0)
					.getPermission()).getCode());
		}
	}
	
	@Test
	public void grantRightsTrialObjectWithOutScopeReadTest() {
		Trial trial = factory.getTrial();
		trial.setId(1);

		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, true, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());

		for (Acl acl : acls) {
			assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());
			assertEquals(trial.getClass().getCanonicalName(), acl
					.getObjectIdentity().getType());

			List<AccessControlEntry> entries = acl.getEntries();
			assertEquals(1, entries.size());
			assertEquals('R', ((PermissionHibernate) entries.get(0)
					.getPermission()).getCode());
		}
	}
	
	@Test
	public void grantRightsTrialObjectWithOutScopeWriteTest() {
		Trial trial = factory.getTrial();
		trial.setId(1);

		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, true, false, false, false,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());

		for (Acl acl : acls) {
			assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());
			assertEquals(trial.getClass().getCanonicalName(), acl
					.getObjectIdentity().getType());

			List<AccessControlEntry> entries = acl.getEntries();
			assertEquals(1, entries.size());
			assertEquals('W', ((PermissionHibernate) entries.get(0)
					.getPermission()).getCode());
		}
	}
	
	
	@Test
	public void grantRightsTrialObjectWithOutScopeAllTest() {
		Trial trial = factory.getTrial();
		trial.setId(1);

		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, true, false, true, true,
				false, false, false, false, false, null);
		roleDao.create(role);

		Login login = factory.getLogin();
		login.getRoles().add(role);
		loginDao.create(login);

		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, null);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());

		for (Acl acl : acls) {
			assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());
			assertEquals(trial.getClass().getCanonicalName(), acl
					.getObjectIdentity().getType());

			List<AccessControlEntry> entries = acl.getEntries();
			assertEquals(3, entries.size());
			List<Character> permissions = new ArrayList<Character>();
			permissions.add('R');
			permissions.add('W');
			permissions.add('A');
			for (AccessControlEntry ace : acl.getEntries()) {
				assertTrue(permissions.contains(((PermissionHibernate) ace
						.getPermission()).getCode()));
				assertTrue(permissions
						.remove((Character) ((PermissionHibernate) ace
								.getPermission()).getCode()));
			}
		}
	}
	

	@Test
	public void grantRightsTrialObjectWithScopeTest() {
		fail();
	}

	@Test
	public void grantRightsTrialSubjectTest() {
		fail();
	}

	@Test
	public void grantRightsUserObjectWithOutScopeTest() {
		fail();
	}

	@Test
	public void grantRightsUserObjectWithScopeTest() {
		fail();
	}

	@Test
	public void registerPersonRoleTest() {
		fail();
	}

	@Test
	public void registerPersonTest() {
		fail();
	}

	@Test
	public void newPersonGrantUserRightsTest() {
		fail();
	}

	@Test
	public void newPersonGrantUserTrialTest() {
		fail();
	}

	@Test
	public void newPersonGrantUserTrialSiteTest() {
		fail();
	}

	@Test
	public void newPersonGrantUserTrialSubjectTest() {
		fail();
	}

}
