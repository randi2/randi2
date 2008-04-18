package de.randi2.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDomainObject implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	@Version
	private int version;
	
	@Transient
	private boolean filter;
	
	protected AbstractDomainObject(){
		this.filter = false;
	}
	
	protected AbstractDomainObject(boolean _filter){
		this.filter = _filter;
	}
	
	public abstract HashMap<String, String> getFilterMap();

	public long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public boolean isFilter() {
		return filter;
	}

	public void setId(long _id) {
		this.id = _id;
	}

	public void setVersion(int _version) {
		this.version = _version;
	}
	
	
}
