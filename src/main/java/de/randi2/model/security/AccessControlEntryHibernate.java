package de.randi2.model.security;


import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.Acl;


@Entity
@Data
public class AccessControlEntryHibernate implements AccessControlEntry, Serializable {

	private static final long serialVersionUID = 6843101926609540483L;
	
	@ManyToOne(targetEntity=AclHibernate.class)
	private Acl acl;
	
	@Embedded
	private PermissionHibernate permission;
	
	private String roleName;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@ManyToOne(targetEntity=SidHibernate.class)
	private SidHibernate sid;
	private boolean granting = true;
	
	
}
