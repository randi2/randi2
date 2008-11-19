package de.randi2.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import de.randi2.model.security.PermissionHibernate;

@Entity
public class Role2 extends AbstractDomainObject {

	public static final Role2 ROLE_INVESTIGATOR = new Role2(
			GrantedAuthorityEnum.ROLE_INVESTIGATOR.toString(), true, false,
			false, true, false, false, true, true, false, false, true, false,
			false, false, true, false, true, false, false, true, false,
			new ArrayList<Role2>());
	public static final Role2 ROLE_P_INVESTIGATOR = new Role2(
			GrantedAuthorityEnum.ROLE_P_INVASTIGATOR.toString(), true, true,
			false, true, false, true, true, true, false, true, true, false,
			true, true, true, false, false, false, true, false, false,
			new ArrayList<Role2>());
	public static final Role2 ROLE_STATISTICAN = new Role2(
			GrantedAuthorityEnum.ROLE_STATISTICIAN.toString(), true, false,
			false, true, true, true, true, true, false, false, true, false,
			false, false, true, false, false, false, true, false, false,
			new ArrayList<Role2>());
	public static final Role2 ROLE_MONITOR = new Role2(
			GrantedAuthorityEnum.ROLE_MONITOR.toString(), true, false, false,
			true, true, true, true, true, false, false, true, false, false,
			false, true, false, true, false, false, false, false,
			new ArrayList<Role2>());
	
	public static final Role2 ROLE_ADMIN = new Role2(
			GrantedAuthorityEnum.ROLE_ADMIN.toString(), false, true, false,
			false, true, true, false, false, true, false, false, true, false,
			false, true, false, false, false, false, false, true, null);
	
	public static final Role2 ROLE_ANONYMOUS = new Role2(
			GrantedAuthorityEnum.ROLE_ANONYMOUS.toString(), false, false, false,
			true, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, null);
	
	public static final Role2 ROLE_USER = new Role2(
			GrantedAuthorityEnum.ROLE_USER.toString(), false, false, false,
			false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, null);

	
	
//	@Column(unique=true)
	private String name;

	private boolean scopeTrialSite = true;

	private boolean createTrialSite = false;
	private boolean writeTrialSite = false;
	private boolean readTrialSite = true;
	private boolean adminTrialSite = false;

	@Transient
	private Set<PermissionHibernate> trialSitePermissions = null;

	private boolean createUser = false;

	private boolean writeOwnUser = true;
	private boolean readOwnUser = true;
	private boolean adminOwnUser = false;
	@Transient
	private Set<PermissionHibernate> ownUserPermissions = null;

	private boolean writeOtherUser = false;
	private boolean readOtherUser = true;
	private boolean adminOtherUser = false;
	@Transient
	private Set<PermissionHibernate> otherUserPersmissions = null;

	private boolean createTrial = false;
	private boolean writeTrial = false;
	private boolean readTrial = false;
	private boolean adminTrial = false;
	@Transient
	private Set<PermissionHibernate> trialPermissions = null;

	private boolean createTrialSubject = false;
	private boolean writeTrialSubject = false;
	private boolean readTrialSubject = false;
	private boolean adminTrialSubject = false;

	@Transient
	private Set<PermissionHibernate> trialSubjectPermissions = null;

	private boolean createRole = false;
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Role2> rolesToAssign = new ArrayList<Role2>();

	public Role2(String name, boolean scopeTrialSite, boolean createTrialSite,
			boolean writeTrialSite, boolean readTrialSite,
			boolean adminTrialSite, boolean createUser, boolean writeOwnUser,
			boolean readOwnUser, boolean adminOwnUser, boolean writeOtherUser,
			boolean readOtherUser, boolean adminOtherUser, boolean createTrial,
			boolean writeTrial, boolean readTrial, boolean adminTrial,
			boolean createTrialSubject, boolean writeTrialSubject,
			boolean readTrialSubject, boolean adminTrialSubject,
			boolean createRole, List<Role2> rolesToAssign) {
		super();
		this.name = name;
		this.scopeTrialSite = scopeTrialSite;
		this.createTrialSite = createTrialSite;
		this.writeTrialSite = writeTrialSite;
		this.readTrialSite = readTrialSite;
		this.adminTrialSite = adminTrialSite;
		this.createUser = createUser;
		this.writeOwnUser = writeOwnUser;
		this.readOwnUser = readOwnUser;
		this.adminOwnUser = adminOwnUser;
		this.writeOtherUser = writeOtherUser;
		this.readOtherUser = readOtherUser;
		this.adminOtherUser = adminOtherUser;
		this.createTrial = createTrial;
		this.writeTrial = writeTrial;
		this.readTrial = readTrial;
		this.adminTrial = adminTrial;
		this.createTrialSubject = createTrialSubject;
		this.writeTrialSubject = writeTrialSubject;
		this.readTrialSubject = readTrialSubject;
		this.adminTrialSubject = adminTrialSubject;
		this.createRole = createRole;
		this.rolesToAssign = rolesToAssign;
	}

	
	public Role2(){}
	
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
	 * @return the scopeTrialSite
	 */
	public boolean isScopeTrialSite() {
		return scopeTrialSite;
	}

	/**
	 * @param scopeTrialSite
	 *            the scopeTrialSite to set
	 */
	public void setScopeTrialSite(boolean scopeTrialSite) {
		this.scopeTrialSite = scopeTrialSite;
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
	 * @return the trialPermissions
	 */
	public Set<PermissionHibernate> getTrialPermissions() {
		if (trialPermissions == null) {
			trialPermissions = new HashSet<PermissionHibernate>();
			if (writeTrial) {
				trialPermissions.add(PermissionHibernate.WRITE);
			}
			if (readTrial) {
				trialPermissions.add(PermissionHibernate.READ);
			}
			if (adminTrial) {
				trialPermissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}

		return trialPermissions;
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
	public List<Role2> getRolesToAssign() {
		return rolesToAssign;
	}

	/**
	 * @param rolesToAssign
	 *            the rolesToAssign to set
	 */
	public void setRolesToAssign(List<Role2> rolesToAssign) {
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

	/**
	 * @return the trialSubjectPermissions
	 */
	public Set<PermissionHibernate> getTrialSubjectPermissions() {
		if (trialSubjectPermissions == null) {
			trialSubjectPermissions = new HashSet<PermissionHibernate>();
			if (writeTrialSubject) {
				trialSubjectPermissions.add(PermissionHibernate.WRITE);
			}
			if (readTrialSubject) {
				trialSubjectPermissions.add(PermissionHibernate.READ);
			}
			if (adminTrialSubject) {
				trialSubjectPermissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}
		
		return trialSubjectPermissions;
	}

	/**
	 * @return the otherUserPersmissions
	 */
	public Set<PermissionHibernate> getOtherUserPersmissions() {
		if (otherUserPersmissions == null) {
			otherUserPersmissions = new HashSet<PermissionHibernate>();
			if (writeOtherUser) {
				otherUserPersmissions.add(PermissionHibernate.WRITE);
			}
			if (readOtherUser) {
				otherUserPersmissions.add(PermissionHibernate.READ);
			}
			if (adminOtherUser) {
				otherUserPersmissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}
		
		return otherUserPersmissions;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Role2){
			return (((Role2) o).getName().equals(this.name));
		}
		return false;
	}


	@Override
	public String toString() {
		return name;
	}

}
