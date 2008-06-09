package de.randi2.model;

import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.utility.validations.Password;

@Entity
public class Login extends AbstractDomainObject {

	public final static int MAX_USERNAME_LENGTH = 40;
	public final static int MIN_USERNAME_LENGTH = 5;
	public final static int MAX_PASSWORD_LENGTH = 50;
	public final static int MIN_PASSWORD_LENGTH = 8;
	public final static int HASH_PASSWORD_LENGTH = 64;
	
	
	
	@OneToOne(cascade=CascadeType.ALL)
	private Person person = null;
	
	@Column(unique = true)
	private String username = "";
	private String password = null;
	
	private GregorianCalendar lastLoggedIn = null;
	private GregorianCalendar firstLoggedIn = null;
	
	private boolean active = false;

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

	public GregorianCalendar getFirstLoggedIn() {
		return firstLoggedIn;
	}

	public void setFirstLoggedIn(GregorianCalendar firstLoggedIn) {
		this.firstLoggedIn = firstLoggedIn;
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
	
	
	
}
