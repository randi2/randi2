package de.randi2.model;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
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
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.acls.Permission;
import org.springframework.security.userdetails.UserDetails;

import de.randi2.model.security.PermissionHibernate;
import de.randi2.utility.validations.EMailRANDI2;
import de.randi2.utility.validations.Password;

@Entity
// @DateDependence(firstDate = "createdAt", secondDate = "lastLoggedIn")
@NamedQueries( {
		@NamedQuery(name = "login.AllLoginsWithRolesAndTrialSiteScope", query = "select login from Login as login join login.roles role join login.person.trialSite trialSite where role.scopeTrialSiteView = true AND trialSite.id = ? group by login"),
		@NamedQuery(name = "login.AllLoginsWithRolesAndNotTrialSiteScope", query = "select login from Login as login join login.roles role where role.scopeTrialSiteView = false AND not (role.name = 'ROLE_USER') group by login"),
		@NamedQuery(name = "login.LoginsWriteOtherUser", query = "select login from Login as login join login.roles role where role.writeOtherUser = true group by login"),
		@NamedQuery(name = "login.LoginsWithPermission", query = "from Login as login where login.username in (select ace.sid.sidname from AccessControlEntryHibernate as ace where ace.acl.objectIdentity.javaType = ? and ace.acl.objectIdentity.identifier = ? and ace.permission = ?)") })
@EqualsAndHashCode(callSuper=true)
public @Data class  Login extends AbstractDomainObject implements UserDetails {

	private final static long serialVersionUID = -6809229052570773439L;

	public final static byte MAX_WRONG_LOGINS = 3;
	// 15min
	public final static int MILIS_TO_LOCK_USER = 900000;

	public final static int MAX_USERNAME_LENGTH = 40;
	public final static int MIN_USERNAME_LENGTH = 5;
	public final static int MAX_PASSWORD_LENGTH = 30;
	public final static int MIN_PASSWORD_LENGTH = 8;
	public final static int HASH_PASSWORD_LENGTH = 32;

	private Locale prefLocale = null;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotNull
	private Person person = null;

	@Column(unique = true)
	@Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
	// @Pattern(regex="[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.)?)+\\.([a-zA-Z]){2,4}")
	@EMailRANDI2
	@NotEmpty
	private String username = "";

	@Password(max = MAX_PASSWORD_LENGTH, min = MIN_PASSWORD_LENGTH, hash_length = HASH_PASSWORD_LENGTH)
	private String password = null;

	private GregorianCalendar lastLoggedIn = null;

	private boolean active = false;

	private byte numberWrongLogins = 0;
	private GregorianCalendar lockTime;

	// @CollectionOfElements(fetch = FetchType.EAGER)
	// @Enumerated(value = EnumType.STRING)
	// private Set<GrantedAuthorityEnum> roles = new
	// HashSet<GrantedAuthorityEnum>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	@Override
	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority[] gaa = new GrantedAuthorityImpl[roles.size()];
		Iterator<Role> it = roles.iterator();
		int i = 0;
		while (it.hasNext()) {
			GrantedAuthorityImpl ga = new GrantedAuthorityImpl(it.next()
					.getName());
			gaa[i] = ga;

			i++;
		}
		return gaa;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (numberWrongLogins >= MAX_WRONG_LOGINS) {
			return false;
		} else
			return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Adds a role to this login object
	 * 
	 * @param role
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

	public void removeRole(Role role) {
		this.roles.remove(role);
	}

	/**
	 * This method checks, if the Login has the specified role
	 * 
	 * @param role
	 * @return
	 */
	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	/**
	 * This method checks, if the Login has the specified permission
	 * 
	 * @param role
	 * @return
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
	 * @Override public String toString() { return this.getUsername() + " (" +
	 * this.getPerson().toString() + ")"; }
	 */

	@Override
	public String getUIName() {
		return this.getPerson().getSurname() + ", "
				+ this.getPerson().getFirstname();
	}
	
}
