package de.randi2.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import de.randi2.utility.validations.DateDependence;
import de.randi2.utility.validations.Password;

@Entity
@DateDependence(firstDate="registrationDate",secondDate="lastLoggedIn")
public class Login extends AbstractDomainObject implements UserDetails {

	public final static int MAX_USERNAME_LENGTH = 40;
	public final static int MIN_USERNAME_LENGTH = 5;
	public final static int MAX_PASSWORD_LENGTH = 50;
	public final static int MIN_PASSWORD_LENGTH = 8;
	public final static int HASH_PASSWORD_LENGTH = 64;
	
	private Locale prefLocale = null;
	
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Person person = null;
	
	@Column(unique = true)
	private String username = "";
	private String password = null;
	
	private GregorianCalendar lastLoggedIn = null;
	private GregorianCalendar registrationDate = null;
	
	private boolean active = false;

	@CollectionOfElements(fetch=FetchType.EAGER)
	@Enumerated(value=EnumType.STRING) 
	private Set<GrantedAuthorityEnum> rights = new HashSet<GrantedAuthorityEnum>();
	
	@Length(min=MIN_USERNAME_LENGTH, max=MAX_USERNAME_LENGTH)
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



	public GregorianCalendar getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(GregorianCalendar registrationDate) {
		this.registrationDate = registrationDate;
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


	@Password(max=MAX_PASSWORD_LENGTH,min=MIN_PASSWORD_LENGTH,hash_length=HASH_PASSWORD_LENGTH)
	public String getPassword() {
		return password;
	}

	// Just a private setter for the persistence Provider //I've changed it only temporary to public ... (lpotni)
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	public void setPasswordPlaintext(String plaintextPassword){
		
	}

	public Locale getPrefLocale() {
		return prefLocale;
	}

	public void setPrefLocale(Locale prefLocale) {
		this.prefLocale = prefLocale;
	}

	@Override
	public GrantedAuthority[] getAuthorities() {
		rights.add(GrantedAuthorityEnum.ROLE_USER);
		GrantedAuthority[] gaa = new GrantedAuthorityImpl[rights.size()];
		Iterator<GrantedAuthorityEnum> it = rights.iterator();
		int i = 0;
		while (it.hasNext()) {
			GrantedAuthorityImpl ga = new GrantedAuthorityImpl(it.next().toString());
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

	public Set<GrantedAuthorityEnum> getRights() {
		return rights;
	}

	public void setRights(Set<GrantedAuthorityEnum> rights) {
		this.rights = rights;
	}

	
	
	
	
}
