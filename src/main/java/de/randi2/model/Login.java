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

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import de.randi2.utility.validations.DateDependence;
import de.randi2.utility.validations.Password;

@Entity
//@DateDependence(firstDate = "createdAt", secondDate = "lastLoggedIn")
@NamedQueries({
@NamedQuery(name="login.AllLoginsWithRolesAndTrialSiteScope" , query= "select login from Login as login join login.roles role join login.person.trialSite trialSite where role.scopeTrialSiteView = true AND trialSite.id = ? group by login"),
@NamedQuery(name="login.AllLoginsWithRolesAndNotTrialSiteScope" , query= "select login from Login as login join login.roles role where role.scopeTrialSiteView = false AND not (role.name = 'ROLE_USER') group by login"),
@NamedQuery(name="login.LoginsWriteOtherUser", query="select login from Login as login join login.roles role where role.writeOtherUser = true group by login")
})

public class Login extends AbstractDomainObject implements UserDetails {
	
	private final static long serialVersionUID = -6809229052570773439L;

	public final static byte MAX_WRONG_LOGINS=3;
	//15min
	public final static int MILIS_TO_LOCK_USER = 900000;
	
	public final static int MAX_USERNAME_LENGTH = 40;
	public final static int MIN_USERNAME_LENGTH = 5;
	public final static int MAX_PASSWORD_LENGTH = 50;
	public final static int MIN_PASSWORD_LENGTH = 8;
	public final static int HASH_PASSWORD_LENGTH = 64;

	private Locale prefLocale = null;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Person person = null;

	@Column(unique = true)
	private String username = "";
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

	@Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
	@NotEmpty
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public GregorianCalendar getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(GregorianCalendar lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@NotNull
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Password(max = MAX_PASSWORD_LENGTH, min = MIN_PASSWORD_LENGTH, hash_length = HASH_PASSWORD_LENGTH)
	public String getPassword() {
		return password;
	}

	// Just a private setter for the persistence Provider //I've changed it only
	// temporary to public ... (lpotni)
	public void setPassword(String password) {

		this.password = password;
	}

	public void setPasswordPlaintext(String plaintextPassword) {

	}

	public Locale getPrefLocale() {
		return prefLocale;
	}

	public void setPrefLocale(Locale prefLocale) {
		this.prefLocale = prefLocale;
	}

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
		if(numberWrongLogins >=MAX_WRONG_LOGINS){
			return false;
		}else return true;
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
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	/**
	 * This method checks, if the Login has the specified role
	 * 
	 * @param role
	 * @return
	 */
	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	/*@Override
	public String toString() {
		return this.getUsername() + " (" + this.getPerson().toString() + ")";
	}*/

	@Override
	public String getUIName() {
		return this.getPerson().getSurname()+", "+this.getPerson().getFirstname();
	}

	public byte getNumberWrongLogins() {
		return numberWrongLogins;
	}

	public void setNumberWrongLogins(byte numberWrongLogins) {
		this.numberWrongLogins = numberWrongLogins;
	}

	public GregorianCalendar getLockTime() {
		return lockTime;
	}

	public void setLockTime(GregorianCalendar lockTime) {
		this.lockTime = lockTime;
	}
}
