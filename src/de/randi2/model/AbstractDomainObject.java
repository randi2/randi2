package de.randi2.model;

import java.io.Serializable;
import java.util.HashMap;

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
	private Long id;
	
	@Version
	private Integer version;
	
	
	private boolean filter;
	
	protected AbstractDomainObject(){
		this.filter = false;
	}
	
	protected AbstractDomainObject(boolean _filter){
		this.filter = _filter;
	}
	
	public abstract HashMap<String, String> getFilterMap();

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public boolean isFilter() {
		return filter;
	}

	private void setId(Long _id) {
		this.id = _id;
	}

	private void setVersion(Integer _version) {
		this.version = _version;
	}
	
	
}
