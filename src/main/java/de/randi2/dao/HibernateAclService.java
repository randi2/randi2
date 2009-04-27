package de.randi2.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.AclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.sid.GrantedAuthoritySid;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.security.AclHibernate;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.model.security.SidHibernate;

public class HibernateAclService implements AclService {

	
	@Autowired private SessionFactory sessionFactory;

	@Override
	public ObjectIdentity[] findChildren(ObjectIdentity arg0) {
		return null;
	}

	@Override
	public Acl readAclById(ObjectIdentity arg0) throws NotFoundException {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Acl readAclById(ObjectIdentity object, Sid[] sids)
			throws NotFoundException {
		String sidname = null;
		for (Sid sid : sids) {
			if (sid instanceof PrincipalSid) {
				sidname = ((PrincipalSid) sid).getPrincipal();
			} else if (sid instanceof GrantedAuthoritySid) {
				sidname = ((GrantedAuthoritySid) sid).getGrantedAuthority();
			}
			if (sidname != null) {
				List<Acl> list = sessionFactory.getCurrentSession()
				.getNamedQuery("acl.findAclByObjectIdentityAndSid").setParameter(0, sidname)
				.setParameter(1, object.getIdentifier()).setParameter(2, object.getJavaType()).list();
				if (list.size() == 1) {
					return list.get(0);
				}
			}
		}
		throw new NotFoundException("No Acl found");
	}

	@Override
	public Map<?,?> readAclsById(ObjectIdentity[] arg0) throws NotFoundException {
		return null;
	}

	@Override
	public Map<?,?> readAclsById(ObjectIdentity[] arg0, Sid[] arg1)
			throws NotFoundException {
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public AclHibernate createAcl(AbstractDomainObject object, String sidname) {
		AclHibernate acl = new AclHibernate();
		acl.setObjectIdentity(createObjectIdentityIfNotSaved(object));
		acl.setOwner(createSidIfNotSaved(sidname));
		sessionFactory.getCurrentSession().save(acl);
		return acl;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	public AclHibernate createAclwithPermissions(AbstractDomainObject object,
			String sidname, PermissionHibernate[] permissions, String roleName) {
		AclHibernate acl= new AclHibernate();
		acl.setObjectIdentity(createObjectIdentityIfNotSaved(object));
		acl.setOwner(createSidIfNotSaved(sidname));
		List<AclHibernate> list = sessionFactory.getCurrentSession().createQuery("from AclHibernate acl where acl.owner.id = ? and acl.objectIdentity.id = ?").setParameter(0, acl.getOwner().getId())
		.setParameter(1, acl.getObjectIdentity().getId()).list();
		if(list.size() ==1){
			acl = list.get(0);
		}
		
		for (PermissionHibernate permission : permissions) {
			acl.insertAce(permission, roleName);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(acl);
		return acl;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public AclHibernate createAclwithPermissions(AbstractDomainObject object,
			String sidname, PermissionHibernate[] permissions) {
		return createAclwithPermissions(object, sidname, permissions, null);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	private SidHibernate createSidIfNotSaved(String sidname) {
		List<SidHibernate> list = sessionFactory.getCurrentSession().createCriteria(SidHibernate.class).add(Restrictions.eq("sidname", sidname)).list();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			SidHibernate sid = new SidHibernate(sidname);
			sessionFactory.getCurrentSession().save(sid);
			return sid;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	private ObjectIdentityHibernate createObjectIdentityIfNotSaved(
			AbstractDomainObject object) {
		List<ObjectIdentityHibernate> list = sessionFactory.getCurrentSession().createQuery("from ObjectIdentityHibernate where identifier = :identifier and javaType = :javaType")
		.setParameter("identifier", object.getId()).setParameter("javaType", object.getClass()).list();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			ObjectIdentityHibernate oi = new ObjectIdentityHibernate(object
					.getClass(), object.getId());
			sessionFactory.getCurrentSession().save(oi);
			return oi;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void update(AclHibernate acl) {
		sessionFactory.getCurrentSession().update(acl);
	}
}
