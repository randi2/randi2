package de.randi2.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import org.springframework.security.acls.objectidentity.ObjectIdentity;

import de.randi2.model.AbstractDomainObject;

@Entity
@Data
public class ObjectIdentityHibernate implements ObjectIdentity, Serializable {


	private static final long serialVersionUID = -5277986931816599596L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	private Class<? extends AbstractDomainObject> javaType;
	private Long identifier;
	
	public ObjectIdentityHibernate() {
		super();
	}

	public ObjectIdentityHibernate(Class<? extends AbstractDomainObject> javaType, long identifier) {
		super();
		this.javaType = javaType;
		this.identifier = identifier;
	}


	public Class<? extends AbstractDomainObject> getJavaType() {
		
		return javaType;
	}
	
	public void setJavaType(Class<? extends AbstractDomainObject> javaType) {
		this.javaType = javaType;
	}


}
