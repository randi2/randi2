package de.randi2.core.integration.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Ignore;
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
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
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
	public void setUp() {
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e) {
			fail(e.getMessage());
		}
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
	@Ignore
	public void grantRightsTrialObjectWithScopeWriteTest() {
		Trial trial = factory.getTrial();
		trial.setId(34);

		Login login = factory.getLogin();
		entityManager.persist(login);

		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		Role role1 = new Role("ROLE_Name d3", false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, true, true, false, false,
				false, false, false, false, false, false, null);
		entityManager.persist(role1);
		site1.getMembers().add(login.getPerson());
		site1 = entityManager.merge(site1);
		// Logins site1
		login.getRoles().add(role1);
		login = entityManager.merge(login);
		// Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, site1);

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

	@Ignore
	@Test
	public void grantRightsTrialObjectWithScopeReadTest() {
		Trial trial = factory.getTrial();
		trial.setId(345);

		Login login = factory.getLogin();
		entityManager.persist(login);

		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		Role role1 = new Role("Role_name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, true, true, false,
				false, false, false, false, false, null);
		entityManager.persist(role1);
		site1.getMembers().add(login.getPerson());
		site1 = entityManager.merge(site1);
		// Logins site1
		login.getRoles().add(role1);
		login = entityManager.merge(login);
		// Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, site1);

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
	public void grantRightsTrialObjectWithScopeAllTest() {
		Trial trial = factory.getTrial();
		trial.setId(34);

		Login login = factory.getLogin();
		entityManager.persist(login);

		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		Role role1 = new Role("ROLE_Name d3", false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, true, true, true, true,
				false, false, false, false, false, false, null);
		entityManager.persist(role1);
		site1.getMembers().add(login.getPerson());
		site1 = entityManager.merge(site1);
		// Logins site1
		login.getRoles().add(role1);
		login = entityManager.merge(login);
		// Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		Sid sid = new PrincipalSid(login.getUsername());

		rolesAndRights.grantRights(trial, site1);

		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(1, acls.size());

		for (Acl acl : acls) {
			assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());
			assertEquals(trial.getClass().getCanonicalName(), acl
					.getObjectIdentity().getType());

			List<AccessControlEntry> entries = acl.getEntries();
			assertEquals(2, entries.size());
			List<Character> permissions = new ArrayList<Character>();
			permissions.add('R');
			permissions.add('W');
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
	public void grantRightsTrialSubjectReadTest() {

		Login login = factory.getLogin();
		entityManager.persist(login);

		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, true, false,
				false, false, true, false, false, null);
		roleDao.create(role);
		entityManager.persist(role);
		site1.getMembers().add(login.getPerson());
		site1 = entityManager.merge(site1);
		// Logins site1
		login.getRoles().add(role);
		login = entityManager.merge(login);
		entityManager.flush();
		entityManager.clear();

		Trial trial = factory.getTrial();
		trial.setLeadingSite(site1);
		trial.setSponsorInvestigator(login.getPerson());
		entityManager.persist(trial);

		entityManager.flush();
		entityManager.clear();

		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setPlannedSubjects(10);
		arm1.setTrial(trial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setPlannedSubjects(10);
		arm2.setTrial(trial);
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		trial.setTreatmentArms(arms);
		trial = entityManager.merge(trial);

		rolesAndRights.grantRights(trial, site1);

		// Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		trial = entityManager.find(Trial.class, trial.getId());
		Sid sid = new PrincipalSid(login.getUsername());

		TrialSubject subject = new TrialSubject();
		subject.setArm(trial.getTreatmentArmsList().get(0));
		rolesAndRights.grantRights(subject, site1);
		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			if (acl.getObjectIdentity().getType()
					.equals(Trial.class.getCanonicalName())) {
				assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('R', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			} else {
				assertEquals(subject.getId(), acl.getObjectIdentity().getIdentifier());
				assertEquals(subject.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('R', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			}

		}
	}
	
	
	@Test
	public void grantRightsTrialSubjectWriteTest() {

		Login login = factory.getLogin();
		entityManager.persist(login);

		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, true, false,
				false, true, false, false, false, null);
		roleDao.create(role);
		entityManager.persist(role);
		site1.getMembers().add(login.getPerson());
		site1 = entityManager.merge(site1);
		// Logins site1
		login.getRoles().add(role);
		login = entityManager.merge(login);

		Trial trial = factory.getTrial();
		trial.setLeadingSite(site1);
		trial.setSponsorInvestigator(login.getPerson());
		entityManager.persist(trial);

		entityManager.flush();
		entityManager.clear();

		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setPlannedSubjects(10);
		arm1.setTrial(trial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setPlannedSubjects(10);
		arm2.setTrial(trial);
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		trial.setTreatmentArms(arms);
		trial = entityManager.merge(trial);

		rolesAndRights.grantRights(trial, site1);

		// Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		trial = entityManager.find(Trial.class, trial.getId());
		Sid sid = new PrincipalSid(login.getUsername());

		TrialSubject subject = new TrialSubject();
		subject.setArm(trial.getTreatmentArmsList().get(0));
		rolesAndRights.grantRights(subject, site1);
		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			if (acl.getObjectIdentity().getType()
					.equals(Trial.class.getCanonicalName())) {
				assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('R', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			} else {
				assertEquals(subject.getId(), acl.getObjectIdentity().getIdentifier());
				assertEquals(subject.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('W', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			}

		}
	}


	@Test
	public void grantRightsTrialSubjectAdminTest() {
		Login login = factory.getLogin();
		entityManager.persist(login);

		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		Role role = new Role("ROLE_Name", false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, true, false,
				false, false, false, true, false, null);
		roleDao.create(role);
		entityManager.persist(role);
		site1.getMembers().add(login.getPerson());
		site1 = entityManager.merge(site1);
		// Logins site1
		login.getRoles().add(role);
		login = entityManager.merge(login);

		Trial trial = factory.getTrial();
		trial.setLeadingSite(site1);
		trial.setSponsorInvestigator(login.getPerson());
		entityManager.persist(trial);

		entityManager.flush();
		entityManager.clear();

		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setPlannedSubjects(10);
		arm1.setTrial(trial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setPlannedSubjects(10);
		arm2.setTrial(trial);
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		trial.setTreatmentArms(arms);
		trial = entityManager.merge(trial);

		rolesAndRights.grantRights(trial, site1);

		// Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		trial = entityManager.find(Trial.class, trial.getId());
		Sid sid = new PrincipalSid(login.getUsername());

		TrialSubject subject = new TrialSubject();
		subject.setArm(trial.getTreatmentArmsList().get(0));
		rolesAndRights.grantRights(subject, site1);
		List<AclHibernate> acls = entityManager
				.createQuery("from AclHibernate").getResultList();
		assertEquals(2, acls.size());

		for (Acl acl : acls) {
			if (acl.getObjectIdentity().getType()
					.equals(Trial.class.getCanonicalName())) {
				assertEquals(trial.getId(), acl.getObjectIdentity().getIdentifier());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('R', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			} else {
				assertEquals(subject.getId(), acl.getObjectIdentity().getIdentifier());
				assertEquals(subject.getClass().getCanonicalName(), acl
						.getObjectIdentity().getType());

				List<AccessControlEntry> entries = acl.getEntries();
				assertEquals(1, entries.size());
				assertEquals('A', ((PermissionHibernate) entries.get(0)
						.getPermission()).getCode());
			}

		}
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
