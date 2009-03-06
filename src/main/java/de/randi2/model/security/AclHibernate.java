package de.randi2.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.UnloadedSidException;
import org.springframework.security.acls.sid.Sid;
import org.springframework.util.Assert;

@Entity
@NamedQuery(name = "acl.findAclByObjectIdentityAndSid", query = "select acl from AclHibernate acl where acl.owner.sidname = ? and acl.objectIdentity.identifier = ? and acl.objectIdentity.javaType = ?")
public class AclHibernate implements Acl, Serializable {

	private static final long serialVersionUID = 253176536526673664L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	@ManyToOne(targetEntity = AclHibernate.class)
	private Acl parentAcl;
	@ManyToOne(cascade = CascadeType.ALL)
	private ObjectIdentityHibernate objectIdentity;
	@OneToMany(mappedBy = "acl", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<AccessControlEntryHibernate> aces = new ArrayList<AccessControlEntryHibernate>();
	@ManyToOne
	private SidHibernate owner;
	private boolean entriesInheriting = true;
	//private String roleName;
	@Transient
	private Sid[] loadedSids = null;

	@Override
	public AccessControlEntry[] getEntries() {
		return aces.toArray(new AccessControlEntryHibernate[]{});
	}

	@Override
	public ObjectIdentityHibernate getObjectIdentity() {
		return objectIdentity;
	}

	@Override
	public SidHibernate getOwner() {
		return owner;
	}

	@Override
	public Acl getParentAcl() {
		return parentAcl;
	}

	@Override
	public boolean isEntriesInheriting() {
		return entriesInheriting;
	}

	@Override
	public boolean isGranted(Permission[] permission, Sid[] sids, boolean administrativeMode)
			throws NotFoundException, UnloadedSidException {
		Assert.notEmpty(permission, "Permissions required");
		Assert.notEmpty(sids, "SIDs required");

		if (!this.isSidLoaded(sids)) {
			throw new UnloadedSidException("ACL was not loaded for one or more SID");
		}

		AccessControlEntry firstRejection = null;

		for (int i = 0; i < permission.length; i++) {
			for (int x = 0; x < sids.length; x++) {
				// Attempt to find exact match for this permission mask and SID
				Iterator acesIterator = aces.iterator();
				boolean scanNextSid = true;
				while (acesIterator.hasNext()) {
					AccessControlEntry ace = (AccessControlEntry) acesIterator.next();
					if ((ace.getPermission().getMask() == permission[i].getMask()) && ace.getSid().equals(sids[x])) {
						// Found a matching ACE, so its authorization decision will prevail
						if (ace.isGranting()) {
							// Success
							//if (!administrativeMode) {
							//   auditLogger.logIfNeeded(true, ace);
							//}

							return true;
						} else {
							// Failure for this permission, so stop search
							// We will see if they have a different permission
							// (this permission is 100% rejected for this SID)
							if (firstRejection == null) {
								// Store first rejection for auditing reasons
								firstRejection = ace;
							}

							scanNextSid = false; // helps break the loop

							break; // exit "aceIterator" while loop
						}
					}
				}

				if (!scanNextSid) {
					break; // exit SID for loop (now try next permission)
				}
			}
		}

		if (firstRejection != null) {
			// We found an ACE to reject the request at this point, as no
			// other ACEs were found that granted a different permission
			//if (!administrativeMode) {
			//  auditLogger.logIfNeeded(false, firstRejection);
			//}

			return false;
		}

		// No matches have been found so far
		if (isEntriesInheriting() && (parentAcl != null)) {
			// We have a parent, so let them try to find a matching ACE
			return parentAcl.isGranted(permission, sids, false);
		} else {
			// We either have no parent, or we're the uppermost parent
			throw new NotFoundException("Unable to locate a matching ACE for passed permissions and SIDs");
		}
	}

	@Override
	public boolean isSidLoaded(Sid[] sids) {
		// If loadedSides is null, this indicates all SIDs were loaded
		// Also return true if the caller didn't specify a SID to find
		if ((this.loadedSids == null) || (sids == null) || (sids.length == 0)) {
			return true;
		}

		// This ACL applies to a SID subset only. Iterate to check it applies.
		for (int i = 0; i < sids.length; i++) {
			boolean found = false;

			for (int y = 0; y < this.loadedSids.length; y++) {
				if (sids[i].equals(this.loadedSids[y])) {
					// this SID is OK
					found = true;

					break; // out of loadedSids for loop
				}
			}

			if (!found) {
				return false;
			}
		}

		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<AccessControlEntryHibernate> getAces() {
		return aces;
	}

	public void setAces(List<AccessControlEntryHibernate> aces) {
		this.aces = aces;
	}

	public void setParentAcl(Acl parentAcl) {
		this.parentAcl = parentAcl;
	}

	public void setObjectIdentity(ObjectIdentityHibernate objectIdentity) {
		this.objectIdentity = objectIdentity;
	}

	public void setOwner(SidHibernate owner) {
		this.owner = owner;
	}

	public void setEntriesInheriting(boolean entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

	public void insertAce(PermissionHibernate permission, String roleName) {
		AccessControlEntryHibernate ace = new AccessControlEntryHibernate();
		ace.setAcl(this);
		ace.setPermission(permission);
		ace.setSid(owner);
		ace.setRoleName(roleName);
		aces.add(ace);
	}
}
