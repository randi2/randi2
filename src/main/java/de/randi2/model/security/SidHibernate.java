package de.randi2.model.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.acls.sid.Sid;

@Entity
public class SidHibernate implements Sid{

	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	public SidHibernate() {
	}

	private String sidname;
	
	
	public SidHibernate(String sidname) {
		this.sidname=sidname;
	}

	public String getSidname() {
		return sidname;
	}

	public void setSidname(String sidname) {
		this.sidname = sidname;
	}

	@Override
	public boolean equals(Object obj) {
		return (this.sidname.hashCode() == obj.hashCode());
	}
	
	@Override
	public int hashCode() {
		return this.sidname.hashCode();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return sidname;
	}


}
