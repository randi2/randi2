package de.randi2.model;

import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Login extends AbstractDomainObject {

	@OneToOne
	private Person person = null;
	
	@Column(unique = true)
	private String username = "";
	private String password = null;
	
	private GregorianCalendar lastLoggedIn = null;
	private GregorianCalendar firstLoggedIn = null;
	
	private boolean active = false;

	
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

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
