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


	private static final long serialVersionUID = -5277986931816599596L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	private Class<? extends AbstractDomainObject> javaType;
	private long identifier;
	
	public ObjectIdentityHibernate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjectIdentityHibernate(Class<? extends AbstractDomainObject> javaType, long identifier) {
		super();
		this.javaType = javaType;
		this.identifier = identifier;
	}


	@Override
	public Class<? extends AbstractDomainObject> getJavaType() {
		
		return javaType;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setJavaType(Class<? extends AbstractDomainObject> javaType) {
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
