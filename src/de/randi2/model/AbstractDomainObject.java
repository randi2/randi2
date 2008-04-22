package de.randi2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDomainObject implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	@Version
	private int version;

	public long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public void setId(long _id) {
		this.id = _id;
	}

	public void setVersion(int _version) {
		this.version = _version;
	}
	
	
}
