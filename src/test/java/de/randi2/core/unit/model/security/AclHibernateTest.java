package de.randi2.core.unit.model.security;

import static de.randi2.utility.security.ArrayListHelper.permissionsOf;
import static de.randi2.utility.security.ArrayListHelper.sidsOf;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;
import de.randi2.model.security.AccessControlEntryHibernate;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.SidHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class AclHibernateTest {

	private AclHibernate acl;

	@Autowired private SessionFactory sessionFactory;
	
	@Before
	public void setUp() {
		acl = new AclHibernate();
		SidHibernate sid = new SidHibernate();
		sid.setSidname("owner");
		acl.setOwner(sid);
	}

	@Test
	public void testParentAcl() {
		assertNull(acl.getParentAcl());
		AclHibernate aclP = new AclHibernate();
		SidHibernate sid = new SidHibernate();
		sid.setSidname("owner2");
		aclP.setOwner(sid);
		acl.setParentAcl(aclP);
		assertNotNull(acl.getParentAcl());
		assertEquals(((SidHibernate) acl.getParentAcl().getOwner())
				.getSidname(), aclP.getOwner().getSidname());
	}

	@Test
	public void testObjectIdentity() {
		assertNull(acl.getObjectIdentity());
		ObjectIdentityHibernate oI = new ObjectIdentityHibernate(Login.class, 1);
		acl.setObjectIdentity(oI);
		assertNotNull(acl.getObjectIdentity());
		assertEquals(oI.getType(), acl.getObjectIdentity().getType());
		assertEquals(oI.getIdentifier(), acl.getObjectIdentity()
				.getIdentifier());
	}

	@Test
	public void testACE() {
		List<AccessControlEntryHibernate> aces = new ArrayList<AccessControlEntryHibernate>();
		for (int i = 0; i < 10; i++) {
			AccessControlEntryHibernate ace = new AccessControlEntryHibernate();
			ace.setRoleName("ROLE_" + i);
			ace.setPermission(PermissionHibernate.READ);
			aces.add(ace);
			acl.insertAce(PermissionHibernate.READ, "ROLE_" + i);
		}

		assertEquals(aces.size(), acl.getAces().size());
		for (AccessControlEntryHibernate ace : acl.getAces()) {
			boolean found = false;
			for (AccessControlEntryHibernate aceSet : aces) {
				if (ace.getPermission().equals(PermissionHibernate.READ)
						&& ace.getRoleName().equals(aceSet.getRoleName())) {
					found = true;
					assertEquals(ace.getAcl(), acl);
					assertEquals(ace.getSid(), acl.getOwner());
				}
			}
			assertTrue("AccesControlEntry not found", found);
		}

		assertEquals(aces.size(), acl.getEntries().size());

	}

	@Test
	public void testIsGranting() {
		for (int i = 0; i < 10; i++) {
			acl.insertAce(PermissionHibernate.READ, "ROLE_" + i);
		}
		AccessControlEntryHibernate ace = new AccessControlEntryHibernate();
		ace.setAcl(acl);
		ace.setGranting(false);
		ace.setPermission(PermissionHibernate.ADMINISTRATION);
		ace.setRoleName("ROLE_NOTGRANTED");
		ace.setSid(acl.getOwner());
		acl.getAces().add(ace);
		assertEquals(11, acl.getEntries().size());

		// run with other permissions and one correct sid
		try {
			acl.isGranted(permissionsOf(PermissionHibernate.CREATE, PermissionHibernate.DELETE, PermissionHibernate.WRITE), sidsOf(new SidHibernate("test1"),new SidHibernate("test2"),new SidHibernate("test3"),new SidHibernate("test4"), acl.getOwner()), false);
			fail("Acl should throw a exception");
		} catch (NotFoundException e) {
		} 

		//run with correct permission and incorrect sid 
		try {
			acl.isGranted(permissionsOf(PermissionHibernate.ADMINISTRATION,PermissionHibernate.CREATE, PermissionHibernate.DELETE, PermissionHibernate.WRITE,PermissionHibernate.READ ), sidsOf(new SidHibernate("test1"),new SidHibernate("test2"),new SidHibernate("test3"),new SidHibernate("test4")), false);
			fail("Acl should throw a exception");
		} catch (NotFoundException e) {
		}

		
		try {
			assertTrue(acl.isGranted(permissionsOf(PermissionHibernate.CREATE, PermissionHibernate.DELETE, PermissionHibernate.WRITE,PermissionHibernate.READ ), sidsOf(new SidHibernate("test1"),new SidHibernate("test2"),new SidHibernate("test3"),new SidHibernate("test4"),acl.getOwner()), false));
		} catch (NotFoundException e) {
			fail("Acl should grant");
		}
		
		//ace granted = false
		try {
			assertFalse(acl.isGranted(permissionsOf(PermissionHibernate.ADMINISTRATION,PermissionHibernate.CREATE, PermissionHibernate.DELETE, PermissionHibernate.WRITE), sidsOf(new SidHibernate("test1"),new SidHibernate("test2"),new SidHibernate("test3"),new SidHibernate("test4"),acl.getOwner()), false));
		} catch (NotFoundException e) {
			fail("Acl should grant");
		}
		

	}
	
	@Test
	public void testEntriesInheriting(){
		assertTrue(acl.isEntriesInheriting());
		acl.setEntriesInheriting(false);
		assertFalse(acl.isEntriesInheriting());
	}

	@Test
	@Transactional
	public void databaseIntegrationTest() {
		List<AccessControlEntryHibernate> aces = new ArrayList<AccessControlEntryHibernate>();
		for (int i = 0; i < 10; i++) {
			AccessControlEntryHibernate ace = new AccessControlEntryHibernate();
			ace.setRoleName("ROLE_" + i);
			ace.setPermission(PermissionHibernate.READ);
			ace.setAcl(acl);
			ace.setSid(acl.getOwner());
			aces.add(ace);
		}
		acl.setAces(aces);
		acl.setObjectIdentity(new ObjectIdentityHibernate(Login.class, -1));
		
		sessionFactory.getCurrentSession().persist(acl.getOwner());
		assertTrue(acl.getOwner().getId()>0);
		sessionFactory.getCurrentSession().persist(acl);
		assertTrue(acl.getId()>0);
		for(AccessControlEntryHibernate ace : acl.getAces()){
			assertTrue(ace.getId()>0);
		}
		AclHibernate dbAcl = (AclHibernate) sessionFactory.getCurrentSession().get(AclHibernate.class, acl.getId());
		assertEquals(acl.getId(), dbAcl.getId());
		assertEquals(acl.getOwner().getSidname(), dbAcl.getOwner().getSidname());
		assertEquals(acl.getOwner().getId(), dbAcl.getOwner().getId());
		
		assertEquals(acl.getAces().size(), dbAcl.getAces().size());
		
		for(AccessControlEntryHibernate ace: dbAcl.getAces()){
			assertEquals(PermissionHibernate.READ.getMask(), ace.getPermission().getMask());
			assertEquals(acl.getOwner().getSidname(),((SidHibernate)ace.getSid()).getSidname());
		}
		
	}
}
