package de.randi2.model.security;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static de.randi2.utility.security.ArrayListHelper.permissionsOf;
import static de.randi2.utility.security.ArrayListHelper.sidsOf;

import de.randi2.model.Login;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class AclHibernateTest {

	private AclHibernate acl;
	@Autowired
	private HibernateTemplate hibernateTemplate;

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
		assertEquals(oI.getJavaType(), acl.getObjectIdentity().getJavaType());
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
		List<PermissionHibernate> permissions = new ArrayList<PermissionHibernate>();
		permissions.add(PermissionHibernate.CREATE);
		permissions.add(PermissionHibernate.DELETE);
		permissions.add(PermissionHibernate.WRITE);
		List<SidHibernate> sids = new ArrayList<SidHibernate>();
		sids.add(new SidHibernate("test1"));
		sids.add(new SidHibernate("test2"));
		sids.add(new SidHibernate("test3"));
		sids.add(new SidHibernate("test4"));
		sids.add(acl.getOwner());
		try {
			acl.isGranted(permissionsOf(), sidsOf(), false);
			fail("Acl should throw a exception");
		} catch (NotFoundException e) {
		}

		//run with correct permission and incorrect sid 
		permissions = new ArrayList<PermissionHibernate>();
		permissions.add(PermissionHibernate.ADMINISTRATION);
		permissions.add(PermissionHibernate.CREATE);
		permissions.add(PermissionHibernate.DELETE);
		permissions.add(PermissionHibernate.WRITE);
		permissions.add(PermissionHibernate.READ);
		sids = new ArrayList<SidHibernate>();
		sids.add(new SidHibernate("test1"));
		sids.add(new SidHibernate("test2"));
		sids.add(new SidHibernate("test3"));
		sids.add(new SidHibernate("test4"));
		try {
			acl.isGranted(permissionsOf(), sidsOf(), false);
			fail("Acl should throw a exception");
		} catch (NotFoundException e) {
		}

		permissions = new ArrayList<PermissionHibernate>();
		permissions.add(PermissionHibernate.CREATE);
		permissions.add(PermissionHibernate.DELETE);
		permissions.add(PermissionHibernate.WRITE);
		permissions.add(PermissionHibernate.READ);
		sids = new ArrayList<SidHibernate>();
		sids.add(new SidHibernate("test1"));
		sids.add(new SidHibernate("test2"));
		sids.add(new SidHibernate("test3"));
		sids.add(new SidHibernate("test4"));
		sids.add(acl.getOwner());
		try {
			assertTrue(acl.isGranted(permissionsOf(), sidsOf(), false));
		} catch (NotFoundException e) {
			fail("Acl should grant");
		}
		
		//ace granted = false
		permissions = new ArrayList<PermissionHibernate>();
		permissions.add(PermissionHibernate.ADMINISTRATION);
		permissions.add(PermissionHibernate.CREATE);
		permissions.add(PermissionHibernate.DELETE);
		permissions.add(PermissionHibernate.WRITE);
		sids = new ArrayList<SidHibernate>();
		sids.add(new SidHibernate("test1"));
		sids.add(new SidHibernate("test2"));
		sids.add(new SidHibernate("test3"));
		sids.add(new SidHibernate("test4"));
		sids.add(acl.getOwner());
		try {
			assertFalse(acl.isGranted(permissionsOf(), sidsOf(), false));
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
		
		hibernateTemplate.persist(acl.getOwner());
		assertTrue(acl.getOwner().getId()>0);
		hibernateTemplate.persist(acl);
		assertTrue(acl.getId()>0);
		for(AccessControlEntryHibernate ace : acl.getAces()){
			assertTrue(ace.getId()>0);
		}
		AclHibernate dbAcl = (AclHibernate) hibernateTemplate.get(AclHibernate.class, acl.getId());
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
