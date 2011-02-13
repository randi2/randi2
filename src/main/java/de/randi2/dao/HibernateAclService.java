/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.security.AccessControlEntryHibernate;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.SidHibernate;

/**
 * The Class HibernateAclService.
 */
public class HibernateAclService implements AclService {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.acls.AclService#findChildren(org.springframework
	 * .security.acls.objectidentity.ObjectIdentity)
	 */
	@Override
	public List<ObjectIdentity> findChildren(ObjectIdentity arg0) {
		List<ObjectIdentity> list = new ArrayList<ObjectIdentity>();
		for (ObjectIdentityHibernate oi : new ObjectIdentityHibernate[0]) {
			list.add(oi);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.acls.AclService#readAclById(org.springframework
	 * .security.acls.objectidentity.ObjectIdentity)
	 */
	@Override
	public Acl readAclById(ObjectIdentity arg0) throws NotFoundException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.acls.AclService#readAclById(org.springframework
	 * .security.acls.objectidentity.ObjectIdentity,
	 * org.springframework.security.acls.sid.Sid[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Acl readAclById(ObjectIdentity object, List<Sid> sids)
			throws NotFoundException {
		String sidname = null;
		for (Sid sid : sids) {
			if (sid instanceof PrincipalSid) {
				sidname = ((PrincipalSid) sid).getPrincipal();
			} else if (sid instanceof GrantedAuthoritySid) {
				sidname = ((GrantedAuthoritySid) sid).getGrantedAuthority();
			}
			if (sidname != null) {
				List<Acl> list = entityManager
						.createNamedQuery("acl.findAclByObjectIdentityAndSid")
						.setParameter(1, sidname)
						.setParameter(2, object.getIdentifier())
						.setParameter(3, object.getType()).getResultList();
				if (list.size() == 1) {
					return list.get(0);
				}
			}
		}
		throw new NotFoundException("No Acl found");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.acls.AclService#readAclsById(org.springframework
	 * .security.acls.objectidentity.ObjectIdentity[])
	 */
	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> arg0)
			throws NotFoundException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.acls.AclService#readAclsById(org.springframework
	 * .security.acls.objectidentity.ObjectIdentity[],
	 * org.springframework.security.acls.sid.Sid[])
	 */
	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> arg0,
			List<Sid> arg1) throws NotFoundException {
		return null;
	}

	/**
	 * Creates the acl.
	 * 
	 * @param object
	 *            the object
	 * @param sidname
	 *            the sidname
	 * 
	 * @return the acl hibernate
	 */
	public AclHibernate createAcl(AbstractDomainObject object, String sidname) {
		AclHibernate acl = new AclHibernate();
		acl.setObjectIdentity(createObjectIdentityIfNotSaved(object));
		acl.setOwner(createSidIfNotSaved(sidname));
		entityManager.persist(acl);
		return acl;
	}

	/**
	 * Creates the acl with specific permissions.
	 * 
	 * @param object
	 *            the object
	 * @param sidname
	 *            the sidname
	 * @param permissions
	 *            the permissions
	 * @param roleName
	 *            the role name
	 * 
	 * @return the acl hibernate
	 */
	@SuppressWarnings("unchecked")
	public AclHibernate createAclwithPermissions(AbstractDomainObject object,
			String sidname, PermissionHibernate[] permissions, String roleName) {
		AclHibernate acl = new AclHibernate();
		acl.setObjectIdentity(createObjectIdentityIfNotSaved(object));
		acl.setOwner(createSidIfNotSaved(sidname));
		List<AclHibernate> list = entityManager
				.createQuery(
						"from AclHibernate acl where acl.owner.id = ? and acl.objectIdentity.id = ?")
				.setParameter(1, acl.getOwner().getId())
				.setParameter(2, acl.getObjectIdentity().getId())
				.getResultList();
		if (list.size() == 1) {
			acl = list.get(0);
		}

		for (PermissionHibernate permission : permissions) {
			acl.insertAce(permission, roleName);
		}
		acl = entityManager.merge(acl);
		return acl;
	}

	/**
	 * Creates the aclwith permissions.
	 * 
	 * @param object
	 *            the object
	 * @param sidname
	 *            the sidname
	 * @param permissions
	 *            the permissions
	 * 
	 * @return the acl hibernate
	 */
	public AclHibernate createAclwithPermissions(AbstractDomainObject object,
			String sidname, PermissionHibernate[] permissions) {
		return createAclwithPermissions(object, sidname, permissions, null);
	}

	/**
	 * Creates the sid if not saved.
	 * 
	 * @param sidname
	 *            the sidname
	 * 
	 * @return the sid hibernate
	 */
	@SuppressWarnings("unchecked")
	private SidHibernate createSidIfNotSaved(String sidname) {
		List<SidHibernate> list = entityManager
				.createQuery("from SidHibernate sid where sidname = :sidname")
				.setParameter("sidname", sidname).getResultList();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			SidHibernate sid = new SidHibernate(sidname);
			entityManager.persist(sid);
			return sid;
		}
	}

	/**
	 * Creates the object identity if not saved.
	 * 
	 * @param object
	 *            the object
	 * 
	 * @return the object identity hibernate
	 */
	@SuppressWarnings("unchecked")
	private ObjectIdentityHibernate createObjectIdentityIfNotSaved(
			AbstractDomainObject object) {
		List<ObjectIdentityHibernate> list = entityManager
				.createQuery(
						"from ObjectIdentityHibernate where identifier = :identifier and type = :type")
				.setParameter("identifier", object.getId())
				.setParameter("type", object.getClass().getCanonicalName())
				.getResultList();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			ObjectIdentityHibernate oi = new ObjectIdentityHibernate(
					object.getClass(), object.getId());
			entityManager.persist(oi);
			return oi;
		}
	}

	/**
	 * Update the acl.
	 * 
	 * @param acl
	 *            the acl
	 */
	public void update(AclHibernate acl) {
		entityManager.merge(acl);
	}

	public void removeACEs(String sidName, String roleName){
		SidHibernate sid = createSidIfNotSaved(sidName);
		//FIXME use faster implementation 
		List<AccessControlEntryHibernate> aces = entityManager.createQuery("from AccessControlEntryHibernate ace where ace.roleName = ? and ace.sid = ?").setParameter(1, roleName).setParameter(2, sid).getResultList();
		for(AccessControlEntryHibernate ace: aces){
			entityManager.remove(ace);
		}
	}
}
