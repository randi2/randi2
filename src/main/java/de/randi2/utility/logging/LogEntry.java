package de.randi2.utility.logging;

import java.text.SimpleDateFormat;
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

	private long identifier;

	@Lob
	private String value;
	
	@Lob
	private String uiName;

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

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	
	public String getUiName() {
		return uiName;
	}

	public void setUiName(String uiName) {
		this.uiName = uiName;
	}

	public String getTimeAsString(){
		return (new SimpleDateFormat()).format(time.getTime());
	}
	
	
	
	@Override
	public String toString() {
		if (clazz != null)
			return (new SimpleDateFormat()).format(time.getTime()) + " " + username + ": " + action
					+ " object type: " + clazz.getSimpleName() + "(id= "+ identifier+") " + value;
		else
			return (new SimpleDateFormat()).format(time.getTime()) + " " + username + ": " + action;
	}
}
