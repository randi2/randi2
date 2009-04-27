package de.randi2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import de.randi2.model.security.PermissionHibernate;

@Entity
public class Role extends AbstractDomainObject {

	private static final long serialVersionUID = 7986310852028135642L;

	public static final Role ROLE_INVESTIGATOR = new Role(
			"ROLE_INVESTIGATOR", false, true, true, true, false, false, true,
			true, false, true, false, true, false, true, true, false, true,
			false, true, false, true, true, false, true, false, false, false,
			false, new ArrayList<Role>());

	
	public static final Role ROLE_STATISTICAN = new Role("ROLE_STATISTICAN",
			false, true, true, true, false, false, true, true, true, true,
			false, true, false, true, true, false, true, false, true, false,
			true, true, false, false, false, true, false, false, null);

	public static final Role ROLE_MONITOR = new Role("ROLE_MONITOR",
			false, true, true, true, false, false, true, true, true, true,
			false, true, false, true, true, false, true, false, true, false,
			true, true, false, false, false, true, false, false, null);

	public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN", true, false,
			true, true, true, true, true, true, false, false, true, false,
			true, false, true, true, false, false, false, false, false, true,
			false, false, false, false, false, true, null);

	public static final Role ROLE_ANONYMOUS = new Role("ROLE_ANONYMOUS",
			false, false, true, false, false, false, false, false, false,
			false, true, false, false, false, true, false, false, false, false,
			false, false, false, false, false, false, false, false, false, null);

	public static final Role ROLE_USER = new Role("ROLE_USER", false, true,
			false, true, false, false, true, true, false, true, false, true,
			false, true, false, false, true, false, true, false, true, false,
			false, false, false, false, false, false, null);
	
	public static final Role ROLE_P_INVESTIGATOR = new Role("ROLE_P_INVESTIGATOR", false, true, true, true, false, false, true,
			true, false, true, true, true, true, true, true, false, true,
			true, true, true, true, true, false, false, false, true, false,
			false, new ArrayList<Role>(Arrays.asList(new Role[]{Role.ROLE_INVESTIGATOR,Role.ROLE_STATISTICAN,Role.ROLE_MONITOR})));


	// @Column(unique=true)
	private String name;

	// to create trial site is no scope necessary
	private boolean createTrialSite = false;

	// scope for read trial site objects
	private boolean scopeTrialSiteView = true;
	private boolean readTrialSite = true;

	// scope for write trial site objects
	private boolean scopeTrialSiteWrite = false;
	private boolean writeTrialSite = false;

	private boolean adminTrialSite = false;

	@Transient
	private Set<PermissionHibernate> trialSitePermissions = null;

	private boolean writeOwnUser = true;
	private boolean readOwnUser = true;
	private boolean adminOwnUser = false;

	@Transient
	private Set<PermissionHibernate> ownUserPermissions = null;

	// Scope for create user objects
	private boolean scopeUserCreate = true;
	private boolean createUser = false;

	// scope for write user objects
	private boolean scopeUserWrite = false;
	private boolean writeOtherUser = false;

	// scope for read user objects
	private boolean scopeUserRead = true;
	private boolean readOtherUser = true;
	private boolean adminOtherUser = false;

	// scope for trial objects
	private boolean scopeTrialCreat = true;
	private boolean createTrial = false;

	private boolean scopeTrialWrite = true;
	private boolean writeTrial = false;

	private boolean scopeTrialRead = true;
	private boolean readTrial = false;
	private boolean adminTrial = false;

	private boolean createTrialSubject = false;
	private boolean writeTrialSubject = false;
	private boolean readTrialSubject = false;
	private boolean adminTrialSubject = false;

	private boolean createRole = false;
	@ManyToMany
	private List<Role> rolesToAssign = new ArrayList<Role>();


	public Role(String name, boolean createTrialSite,
			boolean scopeTrialSiteView, boolean readTrialSite,
			boolean scopeTrialSiteWrite, boolean writeTrialSite,
			boolean adminTrialSite, boolean writeOwnUser, boolean readOwnUser,
			boolean adminOwnUser, boolean scopeUserCreate, boolean createUser,
			boolean scopeUserWrite, boolean writeOtherUser,
			boolean scopeUserRead, boolean readOtherUser,
			boolean adminOtherUser, boolean scopeTrialCreat,
			boolean createTrial, boolean scopeTrialWrite, boolean writeTrial,
			boolean scopeTrialRead, boolean readTrial, boolean adminTrial,
			boolean createTrialSubject, boolean writeTrialSubject,
			boolean readTrialSubject, boolean adminTrialSubject,
			boolean createRole, List<Role> rolesToAssign) {
		super();
		this.name = name;
		this.createTrialSite = createTrialSite;
		this.scopeTrialSiteView = scopeTrialSiteView;
		this.readTrialSite = readTrialSite;
		this.scopeTrialSiteWrite = scopeTrialSiteWrite;
		this.writeTrialSite = writeTrialSite;
		this.adminTrialSite = adminTrialSite;
		this.writeOwnUser = writeOwnUser;
		this.readOwnUser = readOwnUser;
		this.adminOwnUser = adminOwnUser;
		this.scopeUserCreate = scopeUserCreate;
		this.createUser = createUser;
		this.scopeUserWrite = scopeUserWrite;
		this.writeOtherUser = writeOtherUser;
		this.scopeUserRead = scopeUserRead;
		this.readOtherUser = readOtherUser;
		this.adminOtherUser = adminOtherUser;
		this.scopeTrialCreat = scopeTrialCreat;
		this.createTrial = createTrial;
		this.scopeTrialWrite = scopeTrialWrite;
		this.writeTrial = writeTrial;
		this.scopeTrialRead = scopeTrialRead;
		this.readTrial = readTrial;
		this.adminTrial = adminTrial;
		this.createTrialSubject = createTrialSubject;
		this.writeTrialSubject = writeTrialSubject;
		this.readTrialSubject = readTrialSubject;
		this.adminTrialSubject = adminTrialSubject;
		this.createRole = createRole;
		if(rolesToAssign == null){
			this.rolesToAssign = new ArrayList<Role>();
		}else{
			this.rolesToAssign = rolesToAssign;
		}
	}

	public Role() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param scopeTrialSite
	 *            the scopeTrialSite to set
	 */
	public void setScopeTrialSite(boolean scopeTrialSite) {
		this.scopeTrialSiteView = scopeTrialSite;
	}

	/**
	 * @return the createTrialSite
	 */
	public boolean isCreateTrialSite() {
		return createTrialSite;
	}

	/**
	 * @param createTrialSite
	 *            the createTrialSite to set
	 */
	public void setCreateTrialSite(boolean createTrialSite) {
		this.createTrialSite = createTrialSite;
	}

	/**
	 * @return the writeTrialSite
	 */
	public boolean isWriteTrialSite() {
		return writeTrialSite;
	}

	/**
	 * @param writeTrialSite
	 *            the writeTrialSite to set
	 */
	public void setWriteTrialSite(boolean writeTrialSite) {
		this.writeTrialSite = writeTrialSite;
	}

	/**
	 * @return the readTrialSite
	 */
	public boolean isReadTrialSite() {
		return readTrialSite;
	}

	/**
	 * @param readTrialSite
	 *            the readTrialSite to set
	 */
	public void setReadTrialSite(boolean readTrialSite) {
		this.readTrialSite = readTrialSite;
	}

	/**
	 * @return the adminTrialSite
	 */
	public boolean isAdminTrialSite() {
		return adminTrialSite;
	}

	/**
	 * @param adminTrialSite
	 *            the adminTrialSite to set
	 */
	public void setAdminTrialSite(boolean adminTrialSite) {
		this.adminTrialSite = adminTrialSite;
	}

	/**
	 * @return the createUser
	 */
	public boolean isCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(boolean createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the writeOwnUser
	 */
	public boolean isWriteOwnUser() {
		return writeOwnUser;
	}

	/**
	 * @param writeOwnUser
	 *            the writeOwnUser to set
	 */
	public void setWriteOwnUser(boolean writeOwnUser) {
		this.writeOwnUser = writeOwnUser;
	}

	/**
	 * @return the readOwnUser
	 */
	public boolean isReadOwnUser() {
		return readOwnUser;
	}

	/**
	 * @param readOwnUser
	 *            the readOwnUser to set
	 */
	public void setReadOwnUser(boolean readOwnUser) {
		this.readOwnUser = readOwnUser;
	}

	/**
	 * @return the adminOwnUser
	 */
	public boolean isAdminOwnUser() {
		return adminOwnUser;
	}

	/**
	 * @param adminOwnUser
	 *            the adminOwnUser to set
	 */
	public void setAdminOwnUser(boolean adminOwnUser) {
		this.adminOwnUser = adminOwnUser;
	}

	/**
	 * @return the writeOtherUser
	 */
	public boolean isWriteOtherUser() {
		return writeOtherUser;
	}

	/**
	 * @param writeOtherUser
	 *            the writeOtherUser to set
	 */
	public void setWriteOtherUser(boolean writeOtherUser) {
		this.writeOtherUser = writeOtherUser;
	}

	/**
	 * @return the readOtherUser
	 */
	public boolean isReadOtherUser() {
		return readOtherUser;
	}

	/**
	 * @param readOtherUser
	 *            the readOtherUser to set
	 */
	public void setReadOtherUser(boolean readOtherUser) {
		this.readOtherUser = readOtherUser;
	}

	/**
	 * @return the adminOtherUser
	 */
	public boolean isAdminOtherUser() {
		return adminOtherUser;
	}

	/**
	 * @param adminOtherUser
	 *            the adminOtherUser to set
	 */
	public void setAdminOtherUser(boolean adminOtherUser) {
		this.adminOtherUser = adminOtherUser;
	}

	/**
	 * @return the createTrial
	 */
	public boolean isCreateTrial() {
		return createTrial;
	}

	/**
	 * @param createTrial
	 *            the createTrial to set
	 */
	public void setCreateTrial(boolean createTrial) {
		this.createTrial = createTrial;
	}

	/**
	 * @return the writeTrial
	 */
	public boolean isWriteTrial() {
		return writeTrial;
	}

	/**
	 * @param writeTrial
	 *            the writeTrial to set
	 */
	public void setWriteTrial(boolean writeTrial) {
		this.writeTrial = writeTrial;
	}

	/**
	 * @return the readTrial
	 */
	public boolean isReadTrial() {
		return readTrial;
	}

	/**
	 * @param readTrial
	 *            the readTrial to set
	 */
	public void setReadTrial(boolean readTrial) {
		this.readTrial = readTrial;
	}

	/**
	 * @return the adminTrial
	 */
	public boolean isAdminTrial() {
		return adminTrial;
	}

	/**
	 * @param adminTrial
	 *            the adminTrial to set
	 */
	public void setAdminTrial(boolean adminTrial) {
		this.adminTrial = adminTrial;
	}

	/**
	 * @return the createTrialSubject
	 */
	public boolean isCreateTrialSubject() {
		return createTrialSubject;
	}

	/**
	 * @param createTrialSubject
	 *            the createTrialSubject to set
	 */
	public void setCreateTrialSubject(boolean createTrialSubject) {
		this.createTrialSubject = createTrialSubject;
	}

	/**
	 * @return the writeTrialSubject
	 */
	public boolean isWriteTrialSubject() {
		return writeTrialSubject;
	}

	/**
	 * @param writeTrialSubject
	 *            the writeTrialSubject to set
	 */
	public void setWriteTrialSubject(boolean writeTrialSubject) {
		this.writeTrialSubject = writeTrialSubject;
	}

	/**
	 * @return the readTrialSubject
	 */
	public boolean isReadTrialSubject() {
		return readTrialSubject;
	}

	/**
	 * @param readTrialSubject
	 *            the readTrialSubject to set
	 */
	public void setReadTrialSubject(boolean readTrialSubject) {
		this.readTrialSubject = readTrialSubject;
	}

	/**
	 * @return the adminTrialSubject
	 */
	public boolean isAdminTrialSubject() {
		return adminTrialSubject;
	}

	/**
	 * @param adminTrialSubject
	 *            the adminTrialSubject to set
	 */
	public void setAdminTrialSubject(boolean adminTrialSubject) {
		this.adminTrialSubject = adminTrialSubject;
	}

	/**
	 * @return the createRole
	 */
	public boolean isCreateRole() {
		return createRole;
	}

	/**
	 * @param createRole
	 *            the createRole to set
	 */
	public void setCreateRole(boolean createRole) {
		this.createRole = createRole;
	}

	/**
	 * @return the rolesToAssign
	 */
	public List<Role> getRolesToAssign() {
		return rolesToAssign;
	}

	/**
	 * @param rolesToAssign
	 *            the rolesToAssign to set
	 */
	public void setRolesToAssign(List<Role> rolesToAssign) {
		this.rolesToAssign = rolesToAssign;
	}

	/**
	 * @return the trialSitePermissions
	 */
	public Set<PermissionHibernate> getTrialSitePermissions() {
		if (trialSitePermissions == null) {
			trialSitePermissions = new HashSet<PermissionHibernate>();
			if (writeTrialSite) {
				trialSitePermissions.add(PermissionHibernate.WRITE);
			}
			if (readTrialSite) {
				trialSitePermissions.add(PermissionHibernate.READ);
			}
			if (adminTrialSite) {
				trialSitePermissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}
		return trialSitePermissions;
	}

	/**
	 * @return the ownUserPermissions
	 */
	public Set<PermissionHibernate> getOwnUserPermissions() {
		if (ownUserPermissions == null) {
			ownUserPermissions = new HashSet<PermissionHibernate>();
			if (writeOwnUser) {
				ownUserPermissions.add(PermissionHibernate.WRITE);
			}
			if (readOwnUser) {
				ownUserPermissions.add(PermissionHibernate.READ);
			}
			if (adminOwnUser) {
				ownUserPermissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}

		return ownUserPermissions;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Role) {
			return (((Role) o).getName().equals(this.name));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the scopeTrialSiteView
	 */
	public boolean isScopeTrialSiteView() {
		return scopeTrialSiteView;
	}

	/**
	 * @param scopeTrialSiteView
	 *            the scopeTrialSiteView to set
	 */
	public void setScopeTrialSiteView(boolean scopeTrialSiteView) {
		this.scopeTrialSiteView = scopeTrialSiteView;
	}

	/**
	 * @return the scopeTrialSiteWrite
	 */
	public boolean isScopeTrialSiteWrite() {
		return scopeTrialSiteWrite;
	}

	/**
	 * @param scopeTrialSiteWrite
	 *            the scopeTrialSiteWrite to set
	 */
	public void setScopeTrialSiteWrite(boolean scopeTrialSiteWrite) {
		this.scopeTrialSiteWrite = scopeTrialSiteWrite;
	}

	/**
	 * @return the scopeUserCreate
	 */
	public boolean isScopeUserCreate() {
		return scopeUserCreate;
	}

	/**
	 * @param scopeUserCreate
	 *            the scopeUserCreate to set
	 */
	public void setScopeUserCreate(boolean scopeUserCreate) {
		this.scopeUserCreate = scopeUserCreate;
	}

	/**
	 * @return the scopeUserWrite
	 */
	public boolean isScopeUserWrite() {
		return scopeUserWrite;
	}

	/**
	 * @param scopeUserWrite
	 *            the scopeUserWrite to set
	 */
	public void setScopeUserWrite(boolean scopeUserWrite) {
		this.scopeUserWrite = scopeUserWrite;
	}

	/**
	 * @return the scopeUserRead
	 */
	public boolean isScopeUserRead() {
		return scopeUserRead;
	}

	/**
	 * @param scopeUserRead
	 *            the scopeUserRead to set
	 */
	public void setScopeUserRead(boolean scopeUserRead) {
		this.scopeUserRead = scopeUserRead;
	}

	/**
	 * @return the scopeTrialCreat
	 */
	public boolean isScopeTrialCreat() {
		return scopeTrialCreat;
	}

	/**
	 * @param scopeTrialCreat
	 *            the scopeTrialCreat to set
	 */
	public void setScopeTrialCreat(boolean scopeTrialCreat) {
		this.scopeTrialCreat = scopeTrialCreat;
	}

	/**
	 * @return the scopeTrialWrite
	 */
	public boolean isScopeTrialWrite() {
		return scopeTrialWrite;
	}

	/**
	 * @param scopeTrialWrite
	 *            the scopeTrialWrite to set
	 */
	public void setScopeTrialWrite(boolean scopeTrialWrite) {
		this.scopeTrialWrite = scopeTrialWrite;
	}

	/**
	 * @return the scopeTrialRead
	 */
	public boolean isScopeTrialRead() {
		return scopeTrialRead;
	}

	/**
	 * @param scopeTrialRead
	 *            the scopeTrialRead to set
	 */
	public void setScopeTrialRead(boolean scopeTrialRead) {
		this.scopeTrialRead = scopeTrialRead;
	}

}