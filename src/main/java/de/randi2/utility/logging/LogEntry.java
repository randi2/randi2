package de.randi2.utility.logging;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;

import de.randi2.model.AbstractDomainObject;

@Entity
public class LogEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	private GregorianCalendar time = new GregorianCalendar();

	private String username;
	
	private String action;
	
	private Class<? extends AbstractDomainObject> clazz;
	
	private int identifier;
	
	@Lob
	private String value;
	
	public GregorianCalendar getTime() {
		return time;
	}

	public void setTime(GregorianCalendar time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Class<? extends AbstractDomainObject> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends AbstractDomainObject> clazz) {
		this.clazz = clazz;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	public int getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return time.toString() + " "+ username+ ": " + action + " object type: " + clazz.getSimpleName() +" " + value;
	}
	
}
