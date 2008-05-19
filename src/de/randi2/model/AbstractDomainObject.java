package de.randi2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class AbstractDomainObject implements Serializable{
	
	public final static int NOT_YET_SAVED_ID = Integer.MIN_VALUE;
	
	public final static int MAX_VARCHAR_LENGTH = 255;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id = NOT_YET_SAVED_ID;
	
	@Version
	private int version = Integer.MIN_VALUE;

	public long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	private void setId(long _id) {
		this.id = _id;
	}

	private void setVersion(int _version) {
		this.version = _version;
	}
	
}
