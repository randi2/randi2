package de.randi2.dao;

import java.util.List;
import java.util.Map;

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

	@Autowired
	private HibernateTemplate template;


	@Override
	public ObjectIdentity[] findChildren(ObjectIdentity arg0) {
		return null;
	}

	@Override
	public Acl readAclById(ObjectIdentity arg0) throws NotFoundException {
		return null;
	}

	@Override
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
				List<Acl> list = template.findByNamedQuery(
						"acl.findAclByObjectIdentityAndSid", new Object[] {
								sidname, object.getIdentifier(),
								object.getJavaType() });
				if (list.size() == 1) {
					return list.get(0);
				}
			}
		}
		throw new NotFoundException("No Acl found");
	}

	@Override
	public Map readAclsById(ObjectIdentity[] arg0) throws NotFoundException {
		return null;
	}

	@Override
	public Map readAclsById(ObjectIdentity[] arg0, Sid[] arg1)
			throws NotFoundException {
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public AclHibernate createAcl(AbstractDomainObject object, String sidname) {
		AclHibernate acl = new AclHibernate();
		acl.setObjectIdentity(createObjectIdentityIfNotSaved(object));
		acl.setOwner(createSidIfNotSaved(sidname));
		template.save(acl);
		return acl;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public AclHibernate createAclwithPermissions(AbstractDomainObject object,
			String sidname, PermissionHibernate[] permissions, String roleName) {
		AclHibernate acl= new AclHibernate();
		acl.setObjectIdentity(createObjectIdentityIfNotSaved(object));
		acl.setOwner(createSidIfNotSaved(sidname));
		List<AclHibernate> list = template.find("from AclHibernate acl where acl.owner.id = ? and acl.objectIdentity.id = ?" ,new Object[]{acl.getOwner().getId(),acl.getObjectIdentity().getId()});
		if(list.size() ==1){
			acl = list.get(0);
		}
		
		for (PermissionHibernate permission : permissions) {
			acl.insertAce(permission, roleName);
		}
		template.saveOrUpdate(acl);
		template.flush();
		return acl;
	}
	
	
	public AclHibernate createAclwithPermissions(AbstractDomainObject object,
			String sidname, PermissionHibernate[] permissions) {
		return createAclwithPermissions(object, sidname, permissions, null);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	private SidHibernate createSidIfNotSaved(String sidname) {
		List<SidHibernate> list = template.findByExample(new SidHibernate(
				sidname));
		if (list.size() == 1) {
			return list.get(0);
		} else {
			SidHibernate sid = new SidHibernate(sidname);
			template.save(sid);
			return sid;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	private ObjectIdentityHibernate createObjectIdentityIfNotSaved(
			AbstractDomainObject object) {
		List<ObjectIdentityHibernate> list = template
				.findByExample(new ObjectIdentityHibernate(object.getClass(),
						object.getId()));
		if (list.size() == 1) {
			return list.get(0);
		} else {
			ObjectIdentityHibernate oi = new ObjectIdentityHibernate(object
					.getClass(), object.getId());
			template.save(oi);
			return oi;
		}
	}

	public void update(AclHibernate acl) {
		template.update(acl);
	}
}
