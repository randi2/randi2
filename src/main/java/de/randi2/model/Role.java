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
package de.randi2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.NotEmpty;

import de.randi2.model.security.PermissionHibernate;

/**
 * The Class Role.
 */
@Entity
public class Role extends AbstractDomainObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7986310852028135642L;

	/** The Constant ROLE_INVESTIGATOR. */
	public static final Role ROLE_INVESTIGATOR = new Role("ROLE_INVESTIGATOR",
			false, false, true, true, false, false, true, true, false, true,
			false, true, false, true, true, false, true, false, true, false,
			true, true, false, true, false, false, false, false, null);

	/** The Constant ROLE_STATISTICAN. */
	public static final Role ROLE_STATISTICAN = new Role("ROLE_STATISTICAN",
			false, false, true, true, false, false, true, true, true, true,
			false, true, false, true, true, false, true, false, true, false,
			true, true, false, false, false, true, false, false, null);

	/** The Constant ROLE_MONITOR. */
	public static final Role ROLE_MONITOR = new Role("ROLE_MONITOR", false,
			false, true, true, false, false, true, true, true, true, false,
			true, false, true, true, false, true, false, true, false, true,
			true, false, false, false, true, false, false, null);

	/** The Constant ROLE_ANONYMOUS. */
	public static final Role ROLE_ANONYMOUS = new Role("ROLE_ANONYMOUS", false,
			false, true, false, false, false, false, false, false, false, true,
			false, false, false, true, false, false, false, false, false,
			false, false, false, false, false, false, false, false, null);

	/** The Constant ROLE_USER. */
	public static final Role ROLE_USER = new Role("ROLE_USER", false, true,
			false, true, false, false, true, true, false, true, false, true,
			false, true, false, false, true, false, true, false, true, false,
			false, false, false, false, false, false, null);

	/** The Constant ROLE_P_INVESTIGATOR. */
	public static final Role ROLE_P_INVESTIGATOR = new Role(
			"ROLE_P_INVESTIGATOR", false, false, true, true, false, false,
			true, true, false, true, true, true, true, true, true, false, true,
			true, true, true, true, true, false, false, false, true, false,
			false, ROLE_INVESTIGATOR, ROLE_STATISTICAN, ROLE_MONITOR, ROLE_USER);

	/**
	 * The Constant ROLE_ADMIN. For productive use it is necessary to add this
	 * role to the roles to assign
	 */
	public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN", true, false,
			true, true, true, true, true, true, false, false, true, false,
			true, false, true, true, false, false, false, false, false, true,
			false, false, false, false, false, true, ROLE_INVESTIGATOR,
			ROLE_MONITOR, ROLE_P_INVESTIGATOR, ROLE_STATISTICAN, ROLE_USER);

	/** The name. */
	@Column(unique = true)
	@NotEmpty
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@Getter
	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	@Setter
	private String name;

	// to create trial site is no scope necessary
	/** The create trial site. */

	/**
	 * Checks if is creates the trial site.
	 * 
	 * @return true, if is creates the trial site
	 */
	@Getter
	/**
	 * Sets the creates the trial site.
	 * 
	 * @param createTrialSite
	 *            the new creates the trial site
	 */
	@Setter
	private boolean createTrialSite = false;

	// scope for read trial site objects
	/** The scope trial site view. */

	/**
	 * Checks if is scope trial site view.
	 * 
	 * @return true, if is scope trial site view
	 */
	@Getter
	/**
	 * Sets the scope trial site view.
	 * 
	 * @param scopeTrialSiteView
	 *            the new scope trial site view
	 */
	@Setter
	private boolean scopeTrialSiteView = true;

	/**
	 * Checks if is read trial site.
	 * 
	 * @return true, if is read trial site
	 */
	@Getter
	/**
	 * Sets the read trial site.
	 * 
	 * @param readTrialSite
	 *            the new read trial site
	 */
	@Setter
	private boolean readTrialSite = true;

	// scope for write trial site objects
	/** The scope trial site write. */

	/**
	 * Checks if is scope trial site write.
	 * 
	 * @return true, if is scope trial site write
	 */
	@Getter
	/**
	 * Sets the scope trial site write.
	 * 
	 * @param scopeTrialSiteWrite
	 *            the new scope trial site write
	 */
	@Setter
	private boolean scopeTrialSiteWrite = false;

	/**
	 * Checks if is write trial site.
	 * 
	 * @return true, if is write trial site
	 */
	@Getter
	/**
	 * Sets the write trial site.
	 * 
	 * @param writeTrialSite
	 *            the new write trial site
	 */
	@Setter
	private boolean writeTrialSite = false;

	/**
	 * Checks if is admin trial site.
	 * 
	 * @return true, if is admin trial site
	 */
	@Getter
	/**
	 * Sets the admin trial site.
	 * 
	 * @param adminTrialSite
	 *            the new admin trial site
	 */
	@Setter
	private boolean adminTrialSite = false;

	/** The trial site permissions. */
	@Transient
	private Set<PermissionHibernate> trialSitePermissions = null;

	/**
	 * Checks if is write own user.
	 * 
	 * @return true, if is write own user
	 */
	@Getter
	/**
	 * Sets the write own user.
	 * 
	 * @param writeOwnUser
	 *            the new write own user
	 */
	@Setter
	private boolean writeOwnUser = true;

	/**
	 * Checks if is read own user.
	 * 
	 * @return true, if is read own user
	 */
	@Getter
	/**
	 * Sets the read own user.
	 * 
	 * @param readOwnUser
	 *            the new read own user
	 */
	@Setter
	private boolean readOwnUser = true;

	/**
	 * Checks if is admin own user.
	 * 
	 * @return true, if is admin own user
	 */
	@Getter
	/**
	 * Sets the admin own user.
	 * 
	 * @param adminOwnUser
	 *            the new admin own user
	 */
	@Setter
	private boolean adminOwnUser = false;

	/** The own user permissions. */
	@Transient
	private Set<PermissionHibernate> ownUserPermissions = null;

	// Scope for create user objects
	/** The scope user create. */

	/**
	 * Checks if is scope user create.
	 * 
	 * @return true, if is scope user create
	 */
	@Getter
	/**
	 * Sets the scope user create.
	 * 
	 * @param scopeUserCreate
	 *            the new scope user create
	 */
	@Setter
	private boolean scopeUserCreate = true;

	/**
	 * Checks if is creates the user.
	 * 
	 * @return true, if is creates the user
	 */
	@Getter
	/**
	 * Sets the creates the user.
	 * 
	 * @param createUser
	 *            the new creates the user
	 */
	@Setter
	private boolean createUser = false;

	// scope for write user objects
	/** The scope user write. */

	/**
	 * Checks if is scope user write.
	 * 
	 * @return true, if is scope user write
	 */
	@Getter
	/**
	 * Sets the scope user write.
	 * 
	 * @param scopeUserWrite
	 *            the new scope user write
	 */
	@Setter
	private boolean scopeUserWrite = false;

	/**
	 * Checks if is write other user.
	 * 
	 * @return true, if is write other user
	 */
	@Getter
	/**
	 * Sets the write other user.
	 * 
	 * @param writeOtherUser
	 *            the new write other user
	 */
	@Setter
	private boolean writeOtherUser = false;

	// scope for read user objects
	/** The scope user read. */

	/**
	 * Checks if is scope user read.
	 * 
	 * @return true, if is scope user read
	 */
	@Getter
	/**
	 * Sets the scope user read.
	 * 
	 * @param scopeUserRead
	 *            the new scope user read
	 */
	@Setter
	private boolean scopeUserRead = true;

	/**
	 * Checks if is read other user.
	 * 
	 * @return true, if is read other user
	 */
	@Getter
	/**
	 * Sets the read other user.
	 * 
	 * @param readOtherUser
	 *            the new read other user
	 */
	@Setter
	private boolean readOtherUser = true;

	/**
	 * Checks if is admin other user.
	 * 
	 * @return true, if is admin other user
	 */
	@Getter
	/**
	 * Sets the admin other user.
	 * 
	 * @param adminOtherUser
	 *            the new admin other user
	 */
	@Setter
	private boolean adminOtherUser = false;

	// scope for trial objects
	/** The scope trial creat. */

	/**
	 * Checks if is scope trial creat.
	 * 
	 * @return true, if is scope trial creat
	 */
	@Getter
	/**
	 * Sets the scope trial creat.
	 * 
	 * @param scopeTrialCreat
	 *            the new scope trial creat
	 */
	@Setter
	private boolean scopeTrialCreat = true;

	/**
	 * Checks if is creates the trial.
	 * 
	 * @return true, if is creates the trial
	 */
	@Getter
	/**
	 * Sets the creates the trial.
	 * 
	 * @param createTrial
	 *            the new creates the trial
	 */
	@Setter
	private boolean createTrial = false;

	/**
	 * Checks if is scope trial write.
	 * 
	 * @return true, if is scope trial write
	 */
	@Getter
	/**
	 * Sets the scope trial write.
	 * 
	 * @param scopeTrialWrite
	 *            the new scope trial write
	 */
	@Setter
	private boolean scopeTrialWrite = true;

	/**
	 * Checks if is write trial.
	 * 
	 * @return true, if is write trial
	 */
	@Getter
	/**
	 * Sets the write trial.
	 * 
	 * @param writeTrial
	 *            the new write trial
	 */
	@Setter
	private boolean writeTrial = false;

	/**
	 * Checks if is scope trial read.
	 * 
	 * @return true, if is scope trial read
	 */
	@Getter
	/**
	 * Sets the scope trial read.
	 * 
	 * @param scopeTrialRead
	 *            the new scope trial read
	 */
	@Setter
	private boolean scopeTrialRead = true;

	/**
	 * Checks if is read trial.
	 * 
	 * @return true, if is read trial
	 */
	@Getter
	/**
	 * Sets the read trial.
	 * 
	 * @param readTrial
	 *            the new read trial
	 */
	@Setter
	private boolean readTrial = false;

	/**
	 * Checks if is admin trial.
	 * 
	 * @return true, if is admin trial
	 */
	@Getter
	/**
	 * Sets the admin trial.
	 * 
	 * @param adminTrial
	 *            the new admin trial
	 */
	@Setter
	private boolean adminTrial = false;

	/**
	 * Checks if is creates the trial subject.
	 * 
	 * @return true, if is creates the trial subject
	 */
	@Getter
	/**
	 * Sets the creates the trial subject.
	 * 
	 * @param createTrialSubject
	 *            the new creates the trial subject
	 */
	@Setter
	private boolean createTrialSubject = false;

	/**
	 * Checks if is write trial subject.
	 * 
	 * @return true, if is write trial subject
	 */
	@Getter
	/**
	 * Sets the write trial subject.
	 * 
	 * @param writeTrialSubject
	 *            the new write trial subject
	 */
	@Setter
	private boolean writeTrialSubject = false;

	/**
	 * Checks if is read trial subject.
	 * 
	 * @return true, if is read trial subject
	 */
	@Getter
	/**
	 * Sets the read trial subject.
	 * 
	 * @param readTrialSubject
	 *            the new read trial subject
	 */
	@Setter
	private boolean readTrialSubject = false;

	/**
	 * Checks if is admin trial subject.
	 * 
	 * @return true, if is admin trial subject
	 */
	@Getter
	/**
	 * Sets the admin trial subject.
	 * 
	 * @param adminTrialSubject
	 *            the new admin trial subject
	 */
	@Setter
	private boolean adminTrialSubject = false;

	/**
	 * Checks if is creates the role.
	 * 
	 * @return true, if is creates the role
	 */
	@Getter
	/**
	 * Sets the creates the role.
	 * 
	 * @param createRole
	 *            the new creates the role
	 */
	@Setter
	private boolean createRole = false;

	/** The roles to assign. */
	@ManyToMany
	/**
	 * Gets the roles to assign.
	 * 
	 * @return the roles to assign
	 */
	@Getter
	/**
	 * Sets the roles to assign.
	 * 
	 * @param rolesToAssign
	 *            the new roles to assign
	 */
	@Setter
	private List<Role> rolesToAssign = new ArrayList<Role>();

	/**
	 * Instantiates a new role.
	 */
	public Role() {
	}

	/**
	 * Instantiates a new role.
	 * 
	 * @param name
	 *            the name
	 * @param createTrialSite
	 *            the create trial site
	 * @param scopeTrialSiteView
	 *            the scope trial site view
	 * @param readTrialSite
	 *            the read trial site
	 * @param scopeTrialSiteWrite
	 *            the scope trial site write
	 * @param writeTrialSite
	 *            the write trial site
	 * @param adminTrialSite
	 *            the admin trial site
	 * @param writeOwnUser
	 *            the write own user
	 * @param readOwnUser
	 *            the read own user
	 * @param adminOwnUser
	 *            the admin own user
	 * @param scopeUserCreate
	 *            the scope user create
	 * @param createUser
	 *            the create user
	 * @param scopeUserWrite
	 *            the scope user write
	 * @param writeOtherUser
	 *            the write other user
	 * @param scopeUserRead
	 *            the scope user read
	 * @param readOtherUser
	 *            the read other user
	 * @param adminOtherUser
	 *            the admin other user
	 * @param scopeTrialCreat
	 *            the scope trial creat
	 * @param createTrial
	 *            the create trial
	 * @param scopeTrialWrite
	 *            the scope trial write
	 * @param writeTrial
	 *            the write trial
	 * @param scopeTrialRead
	 *            the scope trial read
	 * @param readTrial
	 *            the read trial
	 * @param adminTrial
	 *            the admin trial
	 * @param createTrialSubject
	 *            the create trial subject
	 * @param writeTrialSubject
	 *            the write trial subject
	 * @param readTrialSubject
	 *            the read trial subject
	 * @param adminTrialSubject
	 *            the admin trial subject
	 * @param createRole
	 *            the create role
	 * @param rolesToAssign
	 *            the roles to assign
	 */
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
			boolean createRole, Role... rolesToAssign) {
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
		if (rolesToAssign == null) {
			this.rolesToAssign = new ArrayList<Role>();
		} else {
			this.rolesToAssign = new ArrayList<Role>(Arrays
					.asList(rolesToAssign));
		}
	}

	/**
	 * Get trial site permissions.
	 * 
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
	 * Get own user permissions.
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.AbstractDomainObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass().isInstance(this)) {
			return ((Role) o).getName().equals(this.getName());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.AbstractDomainObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return name;
	}

}