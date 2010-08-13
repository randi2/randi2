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

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import de.randi2.model.security.PermissionHibernate;
import de.randi2.utility.validations.EMailRANDI2;
import de.randi2.utility.validations.Password;

/**
 * The Class Login.
 */
@Entity
@NamedQueries( {
		@NamedQuery(name = "login.AllLoginsWithRolesAndTrialSiteScope", query = "select login from Login as login join login.roles role join login.person.trialSite trialSite where role.scopeTrialSiteView = true AND trialSite.id = ? group by login"),
		@NamedQuery(name = "login.AllLoginsWithRolesAndNotTrialSiteScope", query = "select login from Login as login join login.roles role where role.scopeTrialSiteView = false AND not (role.name = 'ROLE_USER') group by login"),
		@NamedQuery(name = "login.LoginsWriteOtherUser", query = "select login from Login as login join login.roles role where role.writeOtherUser = true group by login"),
		@NamedQuery(name = "login.LoginsWithPermission", query = "from Login as login where login.username in (select ace.sid.sidname from AccessControlEntryHibernate as ace where ace.acl.objectIdentity.type = ? and ace.acl.objectIdentity.identifier = ? and ace.permission = ?)") })
@EqualsAndHashCode(callSuper = true)
public @Data
class Login extends AbstractDomainObject implements UserDetails {

	/** The Constant serialVersionUID. */
	private final static long serialVersionUID = -6809229052570773439L;

	/** The Constant MAX_WRONG_LOGINS. */
	public final static byte MAX_WRONG_LOGINS = 3;
	/** The Constant MILIS_TO_LOCK_USER (15 min). */
	public final static int MILIS_TO_LOCK_USER = 900000;

	/** The Constant MAX_USERNAME_LENGTH. */
	public final static int MAX_USERNAME_LENGTH = 100;

	/** The Constant MIN_USERNAME_LENGTH. */
	public final static int MIN_USERNAME_LENGTH = 5;

	/** The Constant MAX_PASSWORD_LENGTH. */
	public final static int MAX_PASSWORD_LENGTH = 30;

	/** The Constant MIN_PASSWORD_LENGTH. */
	public final static int MIN_PASSWORD_LENGTH = 8;

	/** The Constant HASH_PASSWORD_LENGTH. */
	public final static int HASH_PASSWORD_LENGTH = 64;

	/** The pref locale. */
	private Locale prefLocale = null;

	/** The person. */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotNull
	private Person person = null;

	/** The username. */
	@Column(unique = true)
	@Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
	@EMailRANDI2
	@NotEmpty
	private String username = "";

	/** The password. */
	@Password(max = MAX_PASSWORD_LENGTH, min = MIN_PASSWORD_LENGTH, hash_length = HASH_PASSWORD_LENGTH)
	private String password = null;

	/** The last logged in. */
	private GregorianCalendar lastLoggedIn = null;

	/** The active. */
	private boolean active = false;

	/** The number wrong logins. */
	private byte numberWrongLogins = 0;

	/** The lock time. */
	private GregorianCalendar lockTime;

	/** The roles. */
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (Role role : roles) {
			grantedAuthorities.add(new GrantedAuthorityImpl(role.getName()));
		}
		return grantedAuthorities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.userdetails.UserDetails#isAccountNonExpired
	 * ()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		if (numberWrongLogins >= MAX_WRONG_LOGINS) {
			return false;
		} else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired
	 * ()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Adds a role to this login object. 
	 * If the role is unequal to role anonymous the role user added 
	 * and the role anonymous removed automatically.
	 * 
	 * @param role
	 *            the role
	 */
	public void addRole(Role role) {
		if (this.roles != null) {
			this.roles.add(role);
			if (!role.equals(Role.ROLE_ANONYMOUS)) {
				if (this.roles.contains(Role.ROLE_ANONYMOUS)
						&& this.roles.size() > 1) {
					this.roles.remove(Role.ROLE_ANONYMOUS);
				}
				if (!this.roles.contains(Role.ROLE_USER)
						&& !this.roles.contains(Role.ROLE_ANONYMOUS)) {
					this.roles.add(Role.ROLE_USER);
				}
			}
		}
	}

	/**
	 * Removes the role.
	 * 
	 * @param role
	 *            the role
	 */
	public void removeRole(Role role) {
		this.roles.remove(role);
	}

	/**
	 * This method checks, if the Login has the specified role.
	 * 
	 * @param role
	 *            the role
	 * 
	 * @return true, if checks for role
	 */
	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	/**
	 * This method checks, if the Login has the specified permission.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param permission
	 *            the permission
	 * 
	 * @return true, if checks for permission
	 */
	public boolean hasPermission(Class<? extends AbstractDomainObject> clazz,
			Permission permission) {
		// TODO Check permission delete
		boolean hasPermission = false;

		if (clazz.equals(Login.class) || clazz.equals(Person.class)) {

			for (Role r : roles) {
				if (permission.getMask() == PermissionHibernate.CREATE
						.getMask()) {
					hasPermission = hasPermission || r.isCreateUser();
				} else if (permission.getMask() == PermissionHibernate.WRITE
						.getMask()) {
					hasPermission = hasPermission || r.isWriteOtherUser();
				} else if (permission.getMask() == PermissionHibernate.READ
						.getMask()) {
					hasPermission = hasPermission || r.isReadOtherUser();
				} else if (permission.getMask() == PermissionHibernate.ADMINISTRATION
						.getMask()) {
					hasPermission = hasPermission || r.isAdminOtherUser();
				}

			}
		} else if (clazz.equals(TrialSite.class)) {

			for (Role r : roles) {
				if (permission.getMask() == PermissionHibernate.CREATE
						.getMask()) {
					hasPermission = hasPermission || r.isCreateTrialSite();
				} else if (permission.getMask() == PermissionHibernate.WRITE
						.getMask()) {
					hasPermission = hasPermission || r.isWriteTrialSite();
				} else if (permission.getMask() == PermissionHibernate.READ
						.getMask()) {
					hasPermission = hasPermission || r.isReadTrialSite();
				} else if (permission.getMask() == PermissionHibernate.ADMINISTRATION
						.getMask()) {
					hasPermission = hasPermission || r.isAdminTrialSite();
				}

			}
		} else if (clazz.equals(Trial.class)) {

			for (Role r : roles) {
				if (permission.getMask() == PermissionHibernate.CREATE
						.getMask()) {
					hasPermission = hasPermission || r.isCreateTrial();
				} else if (permission.getMask() == PermissionHibernate.WRITE
						.getMask()) {
					hasPermission = hasPermission || r.isWriteTrial();
				} else if (permission.getMask() == PermissionHibernate.READ
						.getMask()) {
					hasPermission = hasPermission || r.isReadTrial();
				} else if (permission.getMask() == PermissionHibernate.ADMINISTRATION
						.getMask()) {
					hasPermission = hasPermission || r.isAdminTrial();
				}

			}
		} else if (clazz.equals(TrialSubject.class)) {

			for (Role r : roles) {
				if (permission.getMask() == PermissionHibernate.CREATE
						.getMask()) {
					hasPermission = hasPermission || r.isCreateTrialSubject();
				} else if (permission.getMask() == PermissionHibernate.WRITE
						.getMask()) {
					hasPermission = hasPermission || r.isWriteTrialSubject();
				} else if (permission.getMask() == PermissionHibernate.READ
						.getMask()) {
					hasPermission = hasPermission || r.isReadTrialSubject();
				} else if (permission.getMask() == PermissionHibernate.ADMINISTRATION
						.getMask()) {
					hasPermission = hasPermission || r.isAdminTrialSubject();
				}
			}
		}

		return hasPermission;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return this.getPerson().getSurname() + ", "
				+ this.getPerson().getFirstname();
	}

}
