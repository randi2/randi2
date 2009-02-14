package de.randi2.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.acls.objectidentity.ObjectIdentity;

import de.randi2.model.AbstractDomainObject;

@Entity
public class ObjectIdentityHibernate implements ObjectIdentity, Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	private Class<? extends AbstractDomainObject> javaType;
	private long identifier;
	
	public ObjectIdentityHibernate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjectIdentityHibernate(Class javaType, long identifier) {
		super();
		this.javaType = javaType;
		this.identifier = identifier;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getJavaType() {
		
		return javaType;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}

	@Override
	public Long getIdentifier() {
		return identifier;
	}

}
